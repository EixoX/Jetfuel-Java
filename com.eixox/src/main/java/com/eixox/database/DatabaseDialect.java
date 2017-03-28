package com.eixox.database;

import java.util.List;

import com.eixox.NameValueCollection;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.SortDirection;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;

public class DatabaseDialect {

	private final char namePrefix;
	private final char nameSuffix;

	public DatabaseDialect(char namePrefix, char nameSuffix) {
		this.namePrefix = namePrefix;
		this.nameSuffix = nameSuffix;
	}

	public boolean supportsOffset() {
		return false;
	}

	protected void appendFilter(DatabaseCommand command, Filter filter) {
		switch (filter.getFilterType()) {
		case EXPRESSION:
			command.text.append("(");
			appendFilter(command, ((FilterExpression) filter).first);
			command.text.append(")");
			break;
		case NODE:
			final FilterNode node = (FilterNode) filter;
			appendFilter(command, node.filter);
			if (node.next != null) {
				switch (node.operation) {
				case AND:
					command.text.append(" AND ");
					break;
				case OR:
					command.text.append(" OR ");
					break;
				default:
					throw new RuntimeException("Unknwon filter operation: "
							+ node.operation);
				}
				appendFilter(command, node.next);
			}
			break;
		case TERM:
			final FilterTerm term = (FilterTerm) filter;

			appendName(command, term.name);
			final Object value = term.value;
			switch (term.comparison) {
			case EQUAL_TO:
				if (value == null)
					command.text.append(" IS NULL");
				else {
					command.text.append(" = ?");
					command.parameters.add(value);
				}
				break;
			case GREATER_OR_EQUAL:
				command.text.append(" >= ?");
				command.parameters.add(value);
				break;
			case GREATER_THAN:
				command.text.append(" > ?");
				command.parameters.add(value);
				break;
			case LIKE:
				command.text.append(" LIKE ?");
				command.parameters.add(value);
				break;
			case LOWER_OR_EQUAL:
				command.text.append(" <= ?");
				command.parameters.add(value);
				break;
			case LOWER_THAN:
				command.text.append(" < ?");
				command.parameters.add(value);
				break;
			case NOT_EQUAL_TO:
				if (value == null)
					command.text.append(" IS NOT NULL");
				else {
					command.text.append(" != ?");
					command.parameters.add(value);
				}
				break;
			case IN:
				command.text.append(" IN (");
				appendList(command, (Iterable<?>) value);
				command.text.append(")");
				break;
			case NOT_IN:
				command.text.append(" NOT IN (");
				appendList(command, (Iterable<?>) value);
				command.text.append(")");
				break;
			case NOT_LIKE:
				command.text.append(" LIKE ?");
				command.parameters.add(value);
				break;
			default:
				break;
			}
			break;
		default:
			throw new RuntimeException("Unknown filter type: "
					+ filter.getFilterType());
		}
	}

	private void appendList(DatabaseCommand command, Iterable<?> iterable) {
		boolean prependComma = false;
		for (Object o : iterable) {
			if (prependComma)
				command.text.append(",");
			else
				prependComma = true;

			command.text.append("?");
			command.parameters.add(o);
		}
	}

	protected void appendName(DatabaseCommand command, String dataName) {
		command.text.append(this.namePrefix);
		command.text.append(dataName);
		command.text.append(this.nameSuffix);
	}

	protected void prependPage(DatabaseCommand command, int pageSize, int pageOrdinal) {
		// do nothing;
	}

	protected void appendPage(DatabaseCommand command, int pageSize, int pageOrdinal) {
		// do nothing;
	}

	public boolean supportsPaging() {
		return false;
	}

	protected void appendSort(DatabaseCommand command, SortExpression sort) {
		appendName(command, sort.first.name);
		if (sort.first.direction == SortDirection.DESCENDING)
			command.text.append(" DESC");
		SortNode node = sort.first.next;
		while (node != null) {
			command.text.append(", ");
			appendName(command, node.name);
			if (node.direction == SortDirection.DESCENDING)
				command.text.append(" DESC");
			node = node.next;
		}
	}

	public DatabaseCommand buildDeleteCommand(String tableName, Filter filter) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("DELETE FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		return command;
	}

	protected void appendValues(DatabaseCommand command, Object[] values, int colCount) {
		command.text.append('?');
		command.parameters.add(values[0]);
		for (int i = 1; i < colCount; i++) {
			command.text.append(',');
			if (i < colCount && values[i] != null) {
				command.text.append('?');
				command.parameters.add(values[i]);
			} else {
				command.text.append("NULL");
			}
		}
	}

	public DatabaseCommand buildInsertCommand(String tableName, List<String> cols, List<Object[]> values) {

		final DatabaseCommand command = new DatabaseCommand();
		final int colCount = cols.size();
		final int rowCount = values.size();

		command.text.append("INSERT INTO ");
		appendName(command, tableName);
		command.text.append(" ( ");
		appendName(command, cols.get(0));
		for (int i = 1; i < colCount; i++) {
			command.text.append(", ");
			appendName(command, cols.get(i));
		}
		command.text.append(") VALUES ");
		command.text.append('(');
		appendValues(command, values.get(0), colCount);
		command.text.append(')');
		for (int i = 1; i < rowCount; i++) {
			command.text.append(", (");
			appendValues(command, values.get(i), colCount);
			command.text.append(')');
		}
		return command;
	}

	public DatabaseCommand buildUpdateCommand(String tableName, NameValueCollection<Object> values, Filter filter) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("UPDATE ");
		appendName(command, tableName);
		command.text.append(" SET ");
		boolean prependComma = false;
		int l = values.size();
		for (int i = 0; i < l; i++) {
			if (prependComma)
				command.text.append(", ");
			else
				prependComma = true;

			appendName(command, values.getKey(i));
			command.text.append("=?");
			command.parameters.add(values.getValue(i));
		}

		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		return command;
	}

	public DatabaseCommand buildSelectCommand(String tableName, Filter filter, SortExpression sort, int pageSize,
			int pageOrdinal) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");

		if (pageSize > 0 && pageOrdinal >= 0)
			prependPage(command, pageSize, pageOrdinal);

		command.text.append(" * FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		if (sort != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, sort);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(command, pageSize, pageOrdinal);
		return command;
	}

	public DatabaseCommand buildSelectCountCommand(String tableName, Filter filter) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT COUNT(*) FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		return command;
	}

	public DatabaseCommand buildSelectExistsCommand(String tableName, Filter filter) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT 1 WHERE EXISTS (SELECT * FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		command.text.append(")");
		return command;
	}

	public DatabaseCommand buildSelectFirstCommand(String tableName, Filter filter, SortExpression sort) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");
		prependPage(command, 1, 0);
		command.text.append(" * FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		if (sort != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, sort);
		}
		appendPage(command, 1, 0);
		return command;
	}

	public DatabaseCommand buildSelectMemberCommand(String tableName, String colName, Filter filter,
			SortExpression sort, int pageSize, int pageOrdinal) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");
		if (pageSize > 0 && pageOrdinal >= 0)
			prependPage(command, pageSize, pageOrdinal);
		appendName(command, colName);
		command.text.append(" FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		if (sort != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, sort);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(command, pageSize, pageOrdinal);
		return command;
	}

	public DatabaseCommand buildSelectFirstMemberCommand(String tableName, String colName, Filter filter,
			SortExpression sort) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");
		prependPage(command, 1, 0);
		appendName(command, colName);
		command.text.append(" FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		if (sort != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, sort);
		}
		appendPage(command, 1, 0);
		return command;
	}

	public DatabaseCommand buildSelectCommand(String tableName, FilterExpression where, SortExpression orderBy,
			int pageSize, int pageOrdinal) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");
		if (pageSize > 0 && pageOrdinal >= 0)
			prependPage(command, pageSize, pageOrdinal);
		command.text.append(" * FROM ");
		appendName(command, tableName);
		if (where != null) {
			command.text.append(" WHERE ");
			appendFilter(command, where);
		}
		if (orderBy != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, orderBy);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(command, pageSize, pageOrdinal);
		return command;
	}

	public DatabaseCommand buildSelectMembersCommand(String tableName, String[] names, FilterExpression filter,
			SortExpression sort, int pageSize, int pageOrdinal) {
		final DatabaseCommand command = new DatabaseCommand();
		command.text.append("SELECT ");
		if (pageSize > 0 && pageOrdinal >= 0)
			prependPage(command, pageSize, pageOrdinal);
		appendName(command, names[0]);
		for (int i = 1; i < names.length; i++) {
			command.text.append(", ");
			appendName(command, names[i]);
		}
		command.text.append(" FROM ");
		appendName(command, tableName);
		if (filter != null) {
			command.text.append(" WHERE ");
			appendFilter(command, filter);
		}
		if (sort != null) {
			command.text.append(" ORDER BY ");
			appendSort(command, sort);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(command, pageSize, pageOrdinal);
		return command;
	}
}
