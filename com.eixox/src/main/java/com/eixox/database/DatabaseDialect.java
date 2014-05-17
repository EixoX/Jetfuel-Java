package com.eixox.database;

import com.eixox.Pair;
import com.eixox.PairList;
import com.eixox.adapters.ValueAdapter;
import com.eixox.data.DataAspect;
import com.eixox.data.DataAspectMember;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;

public abstract class DatabaseDialect {

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
			final DataAspect<?> aspect = term.getAspect();
			final DataAspectMember member = aspect.get(term.getOrdinal());
			final ValueAdapter<?> adapter = member.getValueAdapter();

			appendDataName(builder, member.getDataName());
			final Object value = term.getValue();
			switch (term.getComparison()) {
			case EQUAL_TO:
				if (value == null)
					builder.append(" IS NULL");
				else {
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

	protected abstract void appendDataName(StringBuilder builder, String dataName);

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

	protected abstract void appendPage(StringBuilder builder, int pageSize, int pageOrdinal);

	protected final String buildSelectCommand(DatabaseAspect aspect, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
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

	protected final String buildSelectMemberCommand(DatabaseAspect aspect, int memberOrdinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
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

	protected final String buildDeleteCommand(DatabaseAspect aspect, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("DELETE FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		return builder.toString();
	}

	protected final String buildUpdateCommand(DatabaseAspect aspect, PairList<Integer, Object> values, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("UPDATE ");
		appendDataName(builder, aspect.getDataName());
		builder.append(" SET ");

		final int vCount = values.size();

		Pair<Integer, Object> pair = values.get(0);
		DatabaseAspectMember member = aspect.get(pair.getKey());
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

	protected final String buildInsertCommand(DatabaseAspect aspect, PairList<Integer, Object> values) {

		final StringBuilder builder = new StringBuilder(255);
		final StringBuilder valueBuilder = new StringBuilder(200);

		builder.append("INSERT INTO ");
		appendDataName(builder, aspect.getDataName());
		builder.append(" ( ");

		final int vCount = values.size();

		Pair<Integer, Object> pair = values.get(0);
		DatabaseAspectMember member = aspect.get(pair.getKey());
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

	protected final String buildSelectExists(DatabaseAspect aspect, Filter filter) {
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

	protected final String buildSelectCount(DatabaseAspect aspect, Filter filter) {
		final StringBuilder builder = new StringBuilder(255);
		builder.append("SELECT COUNT(*) FROM ");
		appendDataName(builder, aspect.getDataName());
		if (filter != null) {
			builder.append(" WHERE ");
			appendFilter(builder, filter);
		}
		return builder.toString();
	}

	protected String buildInsertBulk(DatabaseAspect aspect, Iterable<?> items) {

		final StringBuilder builder = new StringBuilder(512);
		final int count = aspect.getCount();
		final int identityOrdinal = aspect.getIdentityOrdinal();
		boolean prependComma = false;

		builder.append("INSERT INTO ");
		appendDataName(builder, aspect.getDataName());
		builder.append("(");

		for (int i = 0; i < count; i++)
			if (i != identityOrdinal) {
				if (prependComma)
					builder.append(", ");
				else
					prependComma = true;

				appendDataName(builder, aspect.getColumnName(i));
			}

		builder.append(") VALUES ");
		prependComma = false;

		for (Object child : items) {

			if (prependComma)
				builder.append(", ");
			else
				prependComma = true;

			builder.append("(");
			boolean prepend2 = false;
			for (int i = 0; i < count; i++)
				if (i != identityOrdinal) {
					DatabaseAspectMember dam = aspect.get(i);
					ValueAdapter<?> valueAdapter = dam.getValueAdapter();
					Object value = dam.getValue(child);
					if (prepend2)
						builder.append(", ");
					else
						prepend2 = true;
					valueAdapter.appendSql(builder, value, dam.isNullable());
				}
			builder.append(")");
		}

		return builder.toString();
	}
}
