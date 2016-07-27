package com.eixox.cassandra;

import java.lang.reflect.Array;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.eixox.data.Column;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;
import com.eixox.data.entities.EntityFilter;
import com.eixox.data.entities.EntityFilterExpression;
import com.eixox.data.entities.EntityFilterNode;
import com.eixox.data.entities.EntityFilterTerm;

public class CassandraCommand {

	public final StringBuilder text = new StringBuilder(1024);
	public final Session session;
	private final DateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
	private final DateFormat date_time_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public CassandraCommand(Session session) {
		this.session = session;
	}

	public final CassandraCommand appendRaw(String cql) {
		this.text.append(cql);
		return this;
	}

	public final CassandraCommand appendNames(String... names) {
		this.text.append(names[0]);
		for (int i = 1; i < names.length; i++) {
			this.text.append(", ");
			this.text.append(names[i]);
		}
		return this;
	}

	public final CassandraCommand appendColumns(List<? extends Column> cols) {
		this.text.append(cols.get(0).getColumnName());
		int s = cols.size();
		for (int i = 1; i < s; i++) {
			this.text.append(", ");
			this.text.append(cols.get(i).getColumnName());
		}
		return this;
	}

	public final CassandraCommand appendNames(List<String> names) {
		this.text.append(names.get(0));
		int s = names.size();
		for (int i = 1; i < s; i++) {
			this.text.append(", ");
			this.text.append(names.get(i));
		}
		return this;
	}

	private synchronized String formatDate(Object dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((java.util.Date) dt);
		if (cal.get(Calendar.YEAR) > 3000)
			cal.set(Calendar.YEAR, 3000);
		String dtString = date_format.format(cal.getTime());
		return dtString;
	}

	private synchronized String formatDateTime(Object dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime((java.util.Date) dt);
		if (cal.get(Calendar.YEAR) > 3000)
			cal.set(Calendar.YEAR, 3000);
		String dtString = date_time_format.format(cal.getTime());
		return dtString;
	}

	@SuppressWarnings("rawtypes")
	public synchronized final void appendValue(Object value) {

		if (value == null) {
			this.text.append("null");
			return;
		}

		Class<?> claz = value.getClass();
		if (Number.class.isAssignableFrom(claz)) {
			this.text.append(value);
			return;
		} else if (UUID.class.isAssignableFrom(claz)) {
			this.text.append("'");
			this.text.append(value);
			this.text.append("'");
			return;
		} else if (Date.class.isAssignableFrom(claz)) {
			this.text.append("'");
			this.text.append(formatDate(value));
			this.text.append("'");
			return;
		} else if (java.util.Date.class.isAssignableFrom(claz)) {
			this.text.append("'");
			this.text.append(formatDateTime(value));
			this.text.append("'");
			return;
		} else if (String.class.isAssignableFrom(claz) ||
				CharSequence.class.isAssignableFrom(claz) ||
				StringBuffer.class.isAssignableFrom(claz) ||
				StringBuilder.class.isAssignableFrom(claz)) {
			this.text.append("'");
			this.text.append(value.toString().replaceAll("'", "''"));
			this.text.append("'");
			return;
		} else if (claz.isArray()) {
			this.text.append("[");
			int s = Array.getLength(value);
			if (s > 0) {
				appendValue(Array.get(value, 0));
				for (int i = 1; i < s; i++) {
					this.text.append(", ");
					appendValue(Array.get(value, i));
				}
			}
			this.text.append("]");
			return;
		} else if (List.class.isAssignableFrom(claz)) {
			this.text.append("{");
			List<?> list = (List) value;
			int s = list.size();
			if (s > 0) {
				appendValue(list.get(0));
				for (int i = 1; i < s; i++) {
					this.text.append(", ");
					appendValue(list.get(i));
				}
			}
			this.text.append("}");
			return;
		} else if (Map.class.isAssignableFrom(claz)) {
			this.text.append("{");
			Map map = (Map) value;
			boolean prependComma = false;
			for (Object key : map.keySet()) {
				if (prependComma)
					this.text.append(", ");
				else
					prependComma = true;
				this.text.append("'");
				this.text.append(key.toString().replaceAll("'", "''"));
				this.text.append("': ");
				appendValue(map.get(key));
			}
			this.text.append("}");
			return;
		} else {
			throw new RuntimeException("Can't CQL serialize " + claz);
		}
	}

	public final void appendEntityFilter(EntityFilter<?> filter) {
		switch (filter.getFilterType()) {
		case EXPRESSION:
			EntityFilterExpression<?> expression = (EntityFilterExpression<?>) filter;
			this.text.append("(");
			appendEntityFilter(expression.first);
			this.text.append(")");
			break;
		case NODE:
			EntityFilterNode<?> node = (EntityFilterNode<?>) filter;
			appendEntityFilter(node.filter);
			if (node.next != null)
				switch (node.operation) {
				case AND:
					this.text.append(" AND ");
					appendEntityFilter(node.next);
					break;
				case OR:
					this.text.append(" OR ");
					appendEntityFilter(node.next);
					break;
				default:
					throw new RuntimeException("Unknwon filter operation " + node.operation);
				}
			break;
		case TERM:
			EntityFilterTerm<?> term = (EntityFilterTerm<?>) filter;
			appendFilter(new FilterTerm(term.aspect.get(term.ordinal).columnName, term.comparison, term.value));
		default:
			break;

		}
	}

	public final void appendFilter(Filter filter) {
		switch (filter.getFilterType()) {
		case EXPRESSION:
			FilterExpression expression = (FilterExpression) filter;
			this.text.append("(");
			appendFilter(expression.first);
			this.text.append(")");
			break;
		case NODE:
			FilterNode node = (FilterNode) filter;
			appendFilter(node.filter);
			if (node.next != null)
				switch (node.operation) {
				case AND:
					this.text.append(" AND ");
					appendFilter(node.next);
					break;
				case OR:
					this.text.append(" OR ");
					appendFilter(node.next);
					break;
				default:
					throw new RuntimeException("Unknwon filter operation " + node.operation);
				}
			break;
		case TERM:
			FilterTerm term = (FilterTerm) filter;
			this.text.append(term.name);
			switch (term.comparison) {
			case EQUAL_TO:
				this.text.append(" = ");
				appendValue(term.value);
				break;
			case GREATER_OR_EQUAL:
				this.text.append(" >= ");
				appendValue(term.value);
				break;
			case GREATER_THAN:
				this.text.append(" > ");
				appendValue(term.value);
				break;
			case IN:
				this.text.append(" IN (");
				appendValue(term.value);
				this.text.append(")");
				break;
			case LOWER_OR_EQUAL:
				this.text.append(" <= ");
				appendValue(term.value);
				break;
			case LOWER_THAN:
				this.text.append(" < ");
				appendValue(term.value);
				break;
			case NOT_EQUAL_TO:
				this.text.append(" != ");
				appendValue(term.value);
				break;
			default:
				throw new RuntimeException("CASSANDRA does not support " + term.comparison);
			}
			break;
		default:
			throw new RuntimeException("Unknwon filter type " + filter.getFilterType());
		}
	}

	public final CassandraCommand appendWhere(Filter filter) {
		if (filter != null) {
			this.text.append(" WHERE ");
			appendFilter(filter);
		}
		return this;
	}

	public final CassandraCommand appendWhere(EntityFilter<?> filter) {
		if (filter != null) {
			this.text.append(" WHERE ");
			appendEntityFilter(filter);
		}
		return this;
	}

	public final CassandraCommand appendSort(SortNode node) {
		text.append(node.name);
		switch (node.direction) {
		case ASC:
			break;
		case DESC:
			this.text.append(" DESC");
			break;
		default:
			throw new RuntimeException("Unknown sort direction " + node.direction);

		}
		if (node.next != null) {
			text.append(", ");
			appendSort(node.next);
		}
		return this;
	}

	public final CassandraCommand appendSort(SortExpression expression) {
		return appendSort(expression.first);
	}

	public final CassandraCommand appendOrderBy(SortExpression sort) {
		if (sort != null) {
			this.text.append(" ORDER BY ");
			appendSort(sort);
		}
		return this;
	}

	public final CassandraCommand appendLimit(int limit) {

		if (limit > 0) {
			this.text.append(" LIMIT ");
			this.text.append(limit);
		}
		return this;
	}

	public final ResultSet execute() {
		return this.session.execute(this.text.toString());
	}

	public final <T> T execute(CassandraCommandProcessor<T> processor) {
		ResultSet rs = this.session.execute(this.text.toString());
		return processor.process(rs);
	}

}
