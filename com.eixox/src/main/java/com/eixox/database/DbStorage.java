package com.eixox.database;

import java.util.List;
import java.util.Properties;

import com.eixox.Convert;
import com.eixox.Pair;
import com.eixox.PairList;
import com.eixox.adapters.ValueAdapter;
import com.eixox.data.DataAspect;
import com.eixox.data.DataAspectMember;
import com.eixox.data.Delete;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.Insert;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class DbStorage<T> extends Storage<T> {

	private final DbEngine	engine;

	public DbStorage(Class<T> claz, String connectionString, Properties properties) {
		super(claz);
		this.engine = new DbEngine(connectionString, properties);
	}

	public final DbEngine getEngine() {
		return this.engine;
	}

	protected final void appendFilter(StringBuilder builder, Filter filter) {
		switch (filter.getFilterType()) {
			case EXPRESSION:
				builder.append("(");
				appendFilter(builder, ((FilterExpression) filter).getFirst());
				builder.append(")");
				break;
			case NODE:
				final FilterNode node = (FilterNode) filter;
				appendFilter(builder, node.getFilter());
				if (node.getNext() != null) {
					switch (node.getOperation()) {
						case AND:
							builder.append(" AND ");
							break;
						case OR:
							builder.append(" OR ");
							break;
						default:
							throw new RuntimeException("Unknwon filter operation: " + node.getOperation());
					}
					appendFilter(builder, node.getNext());
				}
				break;
			case TERM:
				final FilterTerm term = (FilterTerm) filter;
				final DataAspect aspect = term.getAspect();
				final DataAspectMember member = aspect.get(term.getOrdinal());
				final ValueAdapter<?> adapter = member.getValueAdapter();

				appendDataName(builder, member.getDataName());
				final Object value = term.getValue();
				switch (term.getComparison()) {
					case EQUAL_TO:
						if (value == null)
							builder.append(" IS NULL");
						else
						{
							builder.append(" = ");
							adapter.appendSql(builder, value, false);
						}
						break;
					case GREATER_OR_EQUAL:
						builder.append(" >= ");
						adapter.appendSql(builder, value, false);
						break;
					case GREATER_THAN:
						builder.append(" > ");
						adapter.appendSql(builder, value, false);
						break;
					case IN:
						builder.append(" IN ");
						adapter.appendSqlIterable(builder, ((Iterable<?>) value));
						break;
					case LIKE:
						builder.append(" LIKE ");
						adapter.appendSql(builder, value, false);
						break;
					case LOWER_OR_EQUAL:
						builder.append(" <= ");
						adapter.appendSql(builder, value, false);
						break;
					case LOWER_THAN:
						builder.append(" < ");
						adapter.appendSql(builder, value, false);
						break;
					case NOT_EQUAL_TO:
						if (value == null)
							builder.append(" IS NOT NULL");
						else {
							builder.append(" != ");
							adapter.appendSql(builder, value, false);
						}
						break;
					case NOT_IN:
						builder.append(" NOT IN ");
						adapter.appendSqlIterable(builder, ((Iterable<?>) value));
						break;
					case NOT_LIKE:
						builder.append(" LIKE ");
						adapter.appendSql(builder, value, false);
						break;
					default:
						break;
				}
				break;
			default:
				throw new RuntimeException("Unknown filter type: " + filter.getFilterType());

		}

	}

	protected void appendDataName(StringBuilder builder, String dataName) {
		builder.append('`');
		builder.append(dataName);
		builder.append('`');
	}

	protected final void appendSort(StringBuilder builder, SortExpression exp) {
		SortNode node = exp.getNode();
		do {
			appendDataName(builder, node.getColumnName());
			switch (node.getDirection()) {
				case ASCENDING:
					break;
				case DESCENDING:
					builder.append(" DESC");

					break;
				default:
					throw new RuntimeException("Unknown sort direction: " + node.getDirection());

			}
			node = node.getNext();
			if (node != null) {
				builder.append(", ");
			}
		} while (node != null);

	}

	protected void appendPage(StringBuilder builder, int pageSize, int pageOrdinal) {
		if (pageOrdinal > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize * pageOrdinal);
			builder.append(", ");
			builder.append(pageSize);
		}
		else if (pageSize > 0)
		{
			builder.append(" LIMIT ");
			builder.append(pageSize);
		}
	}

	protected final String buildSelectCommand(DataAspect aspect, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT * FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(builder, sort);
		}
		appendPage(builder, pageSize, pageOrdinal);
		return builder.toString();
	}

	protected final String buildSelectMemberCommand(DataAspect aspect, int memberOrdinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT ");
		appendDataName(builder, aspect.getColumnName(memberOrdinal));
		builder.append(" FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(builder, sort);
		}
		appendPage(builder, pageSize, pageOrdinal);
		return builder.toString();
	}

	protected final String buildDeleteCommand(DataAspect aspect, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("DELETE FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		return builder.toString();
	}

	protected final String buildUpdateCommand(DataAspect aspect, PairList<Integer, Object> values, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("UPDATE ");
		appendDataName(builder, aspect.getDataName());
		builder.append(" SET ");

		final int vCount = values.size();

		Pair<Integer, Object> pair = values.get(0);
		DataAspectMember member = aspect.get(pair.getKey());
		appendDataName(builder, member.getDataName());
		builder.append("=");
		member.getValueAdapter().appendSql(builder, pair.getValue(), member.isNullable());

		for (int i = 1; i < vCount; i++) {
			builder.append(", ");
			pair = values.get(i);
			member = aspect.get(pair.getKey());
			appendDataName(builder, member.getDataName());
			builder.append("=");
			member.getValueAdapter().appendSql(builder, pair.getValue(), member.isNullable());
		}

		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		return builder.toString();
	}

	protected final String buildInsertCommand(DataAspect aspect, PairList<Integer, Object> values) {

		final StringBuilder builder = new StringBuilder(255);
		final StringBuilder valueBuilder = new StringBuilder(200);

		builder.append("INSERT INTO ");
		appendDataName(builder, aspect.getDataName());
		builder.append(" ( ");

		final int vCount = values.size();

		Pair<Integer, Object> pair = values.get(0);
		DataAspectMember member = aspect.get(pair.getKey());
		appendDataName(builder, member.getDataName());

		member.getValueAdapter().appendSql(valueBuilder, pair.getValue(), member.isNullable());

		for (int i = 1; i < vCount; i++) {
			builder.append(", ");
			valueBuilder.append(", ");

			pair = values.get(i);
			member = aspect.get(pair.getKey());

			appendDataName(builder, member.getDataName());
			member.getValueAdapter().appendSql(valueBuilder, pair.getValue(), member.isNullable());
		}

		builder.append(") VALUES (");
		builder.append(valueBuilder);
		builder.append(")");

		return builder.toString();
	}

	protected final String buildSelectExists(DataAspect aspect, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT EXISTS (SELECT * FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		builder.append(")");
		return builder.toString();
	}

	protected final String buildSelectCount(DataAspect aspect, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT COUNT(*) FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		return builder.toString();
	}

	@Override
	public final synchronized long executeDelete(Delete delete) {
		final String cmd = buildDeleteCommand(getAspect(), delete.getFilter());
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized long executeUpdate(Update update) {
		final String cmd = buildUpdateCommand(getAspect(), update.getValues(), update.getFilter());
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized long executeInsert(Insert insert) {
		final String cmd = buildInsertCommand(getAspect(), insert);
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized Object executeInsertAndScopeIdentity(Insert insert) {
		final String cmd = buildInsertCommand(getAspect(), insert);
		return this.engine.executeNonQueryAndScopeIdentity(cmd);
	}

	@Override
	public final synchronized void executeSelect(List<T> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final DataAspect aspect = getAspect();
		final String cmd = buildSelectCommand(aspect, filter, sort, pageSize, pageOrdinal);
		this.engine.executeQuery(output, aspect, cmd);
	}

	@Override
	public final synchronized void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final DataAspect aspect = getAspect();
		final String cmd = buildSelectMemberCommand(aspect, memberOrdinal, filter, sort, pageSize, pageOrdinal);
		this.engine.executeQuery(output, aspect, cmd);
	}

	@Override
	public final synchronized long countWhere(Filter filter) {
		final String cmd = buildSelectCount(getAspect(), filter);
		final Object o = engine.executeScalar(cmd);
		return Convert.toLong(o);
	}

	@Override
	public final synchronized boolean existsWhere(Filter filter) {
		final String cmd = buildSelectExists(getAspect(), filter);
		final Object o = engine.executeScalar(cmd);
		return Convert.toBoolean(o);
	}

	@Override
	public final synchronized Object readMemberWhere(int memberOrdinal, Filter filter, SortExpression sort) {
		final DataAspect aspect = getAspect();
		final String cmd = buildSelectMemberCommand(aspect, memberOrdinal, filter, sort, 1, 0);
		return this.engine.executeScalar(cmd);
	}

	@Override
	public final synchronized T readWhere(Filter filter, SortExpression sort) {
		final String cmd = buildSelectCommand(getAspect(), filter, sort, 1, 0);
		return this.engine.executeEntity(getAspect(), cmd);
	}

}
