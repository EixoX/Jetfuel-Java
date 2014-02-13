package com.eixox.data.database;

import java.util.Iterator;
import java.util.List;

import com.eixox.data.ClassStorageColumn;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;

public class PgSqlDialect implements SqlDialect {

	private final void appendName(StringBuilder builder, String name) {
		builder.append('"');
		builder.append(name);
		builder.append('"');
	}

	private final void appendEnumeration(StringBuilder builder, ClassStorageColumn column, Iterable<?> values) {
		Iterator<?> iterator = values.iterator();
		if (iterator.hasNext()) {
			builder.append(column.formatSql(iterator.next()));
			while (iterator.hasNext()) {
				builder.append(", ");
				builder.append(column.formatSql(iterator.next()));
			}
		}
	}

	private final void appendFilter(SqlClassStorage<?> storage, StringBuilder cmd, Filter filter) {
		switch (filter.getFilterType()) {
		case Expression:
			cmd.append("(");
			appendFilter(storage, cmd, ((FilterExpression) filter).getFirst());
			cmd.append(")");
			break;
		case Node:
			final FilterNode node = (FilterNode) filter;
			appendFilter(storage, cmd, node.getFilter());
			final FilterNode next = node.getNext();
			if (next != null) {
				switch (node.getOperation()) {
				case And:
					cmd.append(" AND ");
					appendFilter(storage, cmd, next);
					break;
				case Or:
					cmd.append(" OR ");
					appendFilter(storage, cmd, next);
					break;
				default:
					throw new RuntimeException("Unknown filter operation " + node.getOperation());
				}
			}
			break;
		case Term:
			final FilterTerm term = (FilterTerm) filter;
			final int ordinal = term.getOrdinal();
			appendName(cmd, storage.get(ordinal).getColumnName());
			Object value = term.getValue();
			switch (term.getComparison()) {
			case EqualTo:
				if (value == null)
					cmd.append(" IS NULL");
				else {
					cmd.append(" = ");
					cmd.append(storage.get(ordinal).formatSql(value));
				}
				break;
			case GreaterOrEqual:
				cmd.append(" >= ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			case GreaterThan:
				cmd.append(" > ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			case In:
				cmd.append(" IN (");
				appendEnumeration(cmd, storage.get(ordinal), (Iterable<?>) value);
				cmd.append(")");
				break;
			case Like:
				cmd.append(" LIKE ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			case LowerOrEqual:
				cmd.append(" <= ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			case LowerThan:
				cmd.append(" < ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			case Not_EqualTo:
				if (value == null)
					cmd.append(" IS NOT NULL");
				else {
					cmd.append(" != ");
					cmd.append(storage.get(ordinal).formatSql(value));
				}
				break;
			case Not_In:
				cmd.append(" NOT IN(");
				appendEnumeration(cmd, storage.get(ordinal), (Iterable<?>) value);
				cmd.append(")");
				break;
			case Not_Like:
				cmd.append(" NOT LIKE ");
				cmd.append(storage.get(ordinal).formatSql(value));
				break;
			default:
				throw new RuntimeException("Unknwon filter comparison " + term.getComparison());
			}
			break;
		default:
			throw new RuntimeException("Unknown filter type " + filter.getFilterType());
		}
	}

	private final void appendSort(SqlClassStorage<?> storage, StringBuilder cmd, SortExpression filter) {
		SortNode node = filter.getFirst();
		appendName(cmd, storage.getColumnName(node.getOrdinal()));
		node = node.getNext();
		while (node != null) {
			cmd.append(", ");
			appendName(cmd, storage.getColumnName(node.getOrdinal()));
			node = node.getNext();
		}
	}

	public final String buildSelect(SqlClassStorage<?> storage, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final StringBuilder builder = new StringBuilder(255);
		final int colCount = storage.size();

		builder.append("SELECT ");
		appendName(builder, storage.getColumnName(0));
		for (int i = 1; i < colCount; i++) {
			builder.append(", ");
			appendName(builder, storage.getColumnName(i));
		}
		builder.append(" FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(storage, builder, sort);
		}
		if (pageSize > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize);
			builder.append(" OFFSET ");
			builder.append(pageSize * pageOrdinal);
			
			
		}
		return builder.toString();
	}

	public final String buildSelectMember(SqlClassStorage<?> storage, int ordinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final StringBuilder builder = new StringBuilder(255);

		builder.append("SELECT ");
		appendName(builder, storage.getColumnName(ordinal));
		builder.append(" FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(storage, builder, sort);
		}
		if (pageSize > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize);
			builder.append(" OFFSET ");
			builder.append(pageSize * pageOrdinal);
		}
		return builder.toString();
	}

	public final String buildSelectFirst(SqlClassStorage<?> storage, Filter filter, SortExpression sort) {
		final StringBuilder builder = new StringBuilder(255);
		final int colCount = storage.size();

		builder.append("SELECT ");
		appendName(builder, storage.getColumnName(0));
		for (int i = 1; i < colCount; i++) {
			builder.append(", ");
			appendName(builder, storage.getColumnName(i));
		}
		builder.append(" FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(storage, builder, sort);
		}

		builder.append(" LIMIT 1");
		return builder.toString();
	}

	public final String buildSelectFirstMember(SqlClassStorage<?> storage, int ordinal, Filter filter, SortExpression sort) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT ");
		appendName(builder, storage.getColumnName(ordinal));
		builder.append(" FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		if (sort != null) {
			builder.append(" ORDER BY ");
			appendSort(storage, builder, sort);
		}

		builder.append(" LIMIT 1");
		return builder.toString();
	}

	public final String buildSelectCount(SqlClassStorage<?> storage, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT COUNT(*) FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		return builder.toString();
	}

	public final String buildDelete(SqlClassStorage<?> storage, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("DELETE FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		return builder.toString();
	}

	public final String buildUpdate(SqlClassStorage<?> storage, List<Integer> ordinals, List<Object> values, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		final int colCount = ordinals.size();
		builder.append("UPDATE ");
		appendName(builder, storage.getTableName());
		builder.append(" SET ");
		appendName(builder, storage.getColumnName(ordinals.get(0)));
		builder.append(" = ");
		builder.append(storage.get(ordinals.get(0)).formatSql(values.get(0)));
		for (int i = 1; i < colCount; i++) {
			builder.append(", ");
			appendName(builder, storage.getColumnName(ordinals.get(i)));
			builder.append(" = ");
			builder.append(storage.get(ordinals.get(i)).formatSql(values.get(i)));
		}
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		return builder.toString();
	}

	public final String buildInsert(SqlClassStorage<?> storage, List<Integer> ordinals, List<Object> values) {
		final StringBuilder builder = new StringBuilder(255);
		final int colCount = ordinals.size();
		builder.append("INSERT INTO ");
		appendName(builder, storage.getTableName());
		builder.append(" (");
		appendName(builder, storage.getColumnName(ordinals.get(0)));
		for (int i = 1; i < colCount; i++) {
			builder.append(", ");
			appendName(builder, storage.getColumnName(ordinals.get(i)));
		}
		builder.append(") VALUES (");
		builder.append(storage.get(ordinals.get(0)).formatSql(values.get(0)));
		for (int i = 1; i < colCount; i++) {
			builder.append(", ");
			builder.append(storage.get(ordinals.get(i)).formatSql(values.get(i)));
		}
		builder.append(")");
		return builder.toString();
	}

	public final String buildSelectExists(SqlClassStorage<?> storage, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT EXISTS(SELECT * FROM ");
		appendName(builder, storage.getTableName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(storage, builder, filter);
		}
		builder.append(")");
		return builder.toString();
	}

	public static final PgSqlDialect Instance = new PgSqlDialect();

}
