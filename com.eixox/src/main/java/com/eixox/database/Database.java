package com.eixox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;
import com.eixox.data.entities.EntityFilter;
import com.eixox.data.entities.EntityFilterExpression;
import com.eixox.data.entities.EntityFilterNode;
import com.eixox.data.entities.EntityFilterTerm;
import com.eixox.data.entities.EntitySortNode;

public class Database {

	public final String url;
	public final Properties properties;
	public final Class<?> driverClass;

	public Database(String driverClass, String url) {
		try {
			this.driverClass = Class.forName(driverClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.url = url;
		this.properties = null;
	}

	public Database(String driverClass, String url, Properties properties) {
		try {
			this.driverClass = Class.forName(driverClass);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.url = url;
		this.properties = properties;
	}

	public final Connection getConnection() throws SQLException {
		return this.properties == null ?
				DriverManager.getConnection(this.url) :
				DriverManager.getConnection(this.url, this.properties);
	}

	protected void appendFilter(DatabaseCommand cmd, EntityFilter filter) {
		switch (filter.getFilterType()) {
			case EXPRESSION:
				cmd.text.append("(");
				appendFilter(cmd, ((EntityFilterExpression) filter).first);
				cmd.text.append(")");
				break;
			case NODE:
				final EntityFilterNode node = (EntityFilterNode) filter;
				appendFilter(cmd, node.filter);
				if (node.next != null) {
					switch (node.operation) {
						case AND:
							cmd.text.append(" AND ");
							break;
						case OR:
							cmd.text.append(" OR ");
							break;
						default:
							throw new RuntimeException("Unknwon filter operation: " + node.operation);
					}
					appendFilter(cmd, node.next);
				}
				break;
			case TERM:
				final EntityFilterTerm term = (EntityFilterTerm) filter;
				final EntityAspect aspect = term.getAspect();
				final EntityAspectMember member = aspect.get(term.ordinal);

				appendName(cmd.text, member.columnName);
				final Object value = term.value;
				switch (term.comparison) {
					case EQUAL_TO:
						if (value == null)
							cmd.text.append(" IS NULL");
						else {
							cmd.text.append(" = ?");
							cmd.parameters.add(value);
						}
						break;
					case GREATER_OR_EQUAL:
						cmd.text.append(" >= ?");
						cmd.parameters.add(value);
						break;
					case GREATER_THAN:
						cmd.text.append(" > ?");
						cmd.parameters.add(value);
						break;
					case IN:
						cmd.text.append(" IN (?)");
						cmd.parameters.add(value);
						break;
					case LIKE:
						cmd.text.append(" LIKE ?");
						cmd.parameters.add(value);
						break;
					case LOWER_OR_EQUAL:
						cmd.text.append(" <= ?");
						cmd.parameters.add(value);
						break;
					case LOWER_THAN:
						cmd.text.append(" < ?");
						cmd.parameters.add(value);
						break;
					case NOT_EQUAL_TO:
						if (value == null)
							cmd.text.append(" IS NOT NULL");
						else {
							cmd.text.append(" != ?");
							cmd.parameters.add(value);
						}
						break;
					case NOT_IN:
						cmd.text.append(" NOT IN (?)");
						cmd.parameters.add(value);
						break;
					case NOT_LIKE:
						cmd.text.append(" LIKE ?");
						cmd.parameters.add(value);
						break;
					default:
						break;
				}
				break;
			default:
				throw new RuntimeException("Unknown filter type: " + filter.getFilterType());
		}

	}

	protected void appendName(StringBuilder builder, String dataName) {
		builder.append(dataName);
	}

	protected void appendSort(DatabaseCommand cmd, EntitySortNode sort) {
		do {
			appendName(cmd.text, sort.aspect.getColumnName(sort.ordinal));
			switch (sort.direction) {
				case ASCENDING:
					break;
				case DESCENDING:
					cmd.text.append(" DESC");
					break;
				default:
					throw new RuntimeException("Unknown sort direction: " + sort.direction);
			}
			sort = sort.next;
			if (sort != null) {
				cmd.text.append(", ");
			}
		} while (sort != null);

	}

	protected void appendPage(StringBuilder builder, int pageSize, int pageOrdinal) {
		throw new RuntimeException("Super unfortunate: Database paging and limits are vendor specific!");
	}

	protected DatabaseCommand buildSelectCommand(EntityAspect aspect, EntityFilter filter, EntitySortNode sort, int pageSize, int pageOrdinal) {
		DatabaseCommand cmd = new DatabaseCommand(255);
		cmd.text.append("SELECT * FROM ");
		appendName(cmd.text, aspect.tableName);
		if (filter != null) {
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}
		if (sort != null) {
			cmd.text.append(" ORDER BY ");
			appendSort(cmd, sort);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(cmd.text, pageSize, pageOrdinal);
		return cmd;
	}

	protected DatabaseCommand buildSelectMemberCommand(EntityAspect aspect, int memberOrdinal, EntityFilter filter, EntitySortNode sort, int pageSize, int pageOrdinal) {
		DatabaseCommand cmd = new DatabaseCommand(255);
		cmd.text.append("SELECT ");
		appendName(cmd.text, aspect.getColumnName(memberOrdinal));
		cmd.text.append(" FROM ");
		appendName(cmd.text, aspect.tableName);
		if (filter != null) {
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}
		if (sort != null) {
			cmd.text.append(" ORDER BY ");
			appendSort(cmd, sort);
		}
		if (pageSize > 0 && pageOrdinal >= 0)
			appendPage(cmd.text, pageSize, pageOrdinal);
		return cmd;
	}

	protected DatabaseCommand buildDeleteCommand(EntityAspect aspect, EntityFilter filter) {
		DatabaseCommand cmd = new DatabaseCommand(255);
		cmd.text.append("DELETE FROM ");
		appendName(cmd.text, aspect.tableName);
		if (filter != null) {
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}
		return cmd;
	}

	protected final DatabaseCommand buildUpdateCommand(EntityAspect aspect, Object[] values, EntityFilter filter) {
		DatabaseCommand cmd = new DatabaseCommand(255, aspect.getCount());

		cmd.text.append("UPDATE ");
		appendName(cmd.text, aspect.tableName);
		cmd.text.append(" SET ");
		boolean prependComma = false;

		for (int i = 0; i < values.length; i++)
			if (values[i] != Void.class) {
				if (prependComma)
					cmd.text.append(", ");
				else
					prependComma = true;

				appendName(cmd.text, aspect.getColumnName(i));
				cmd.text.append("=?");
				cmd.parameters.add(values[i]);
			}

		if (filter != null)
		{
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}

		return cmd;
	}

	protected void appendScopeIdentity(DatabaseCommand cmd, EntityAspect aspect) {
		cmd.text.append("; SELECT MAX(");
		appendName(cmd.text, aspect.getColumnName(aspect.identityOrdinal));
		cmd.text.append(") FROM ");
		appendName(cmd.text, aspect.tableName);
	}

	protected DatabaseCommand buildInsertCommand(EntityAspect aspect, Object[] values, boolean scopeIdentity) {
		final DatabaseCommand cmd = new DatabaseCommand();
		final StringBuilder valuesBuilder = new StringBuilder(128);
		cmd.text.append("INSERT INTO ");
		appendName(cmd.text, aspect.tableName);
		boolean prependComma = false;
		cmd.text.append(" ( ");
		for (int i = 0; i < values.length; i++)
			if (values[i] != Void.class) {
				if (prependComma) {
					cmd.text.append(", ");
					valuesBuilder.append(",?");
				}
				else {
					prependComma = true;
					valuesBuilder.append("?");
				}

				appendName(cmd.text, aspect.getColumnName(i));
				cmd.parameters.add(values[i]);
			}

		cmd.text.append(") VALUES (");
		cmd.text.append(valuesBuilder);
		cmd.text.append(")");
		if (scopeIdentity)
			appendScopeIdentity(cmd, aspect);
		return cmd;
	}

	protected DatabaseCommand buildSelectExists(EntityAspect aspect, EntityFilter filter) {

		DatabaseCommand cmd = new DatabaseCommand(155);
		cmd.text.append("SELECT EXISTS (SELECT * FROM ");
		appendName(cmd.text, aspect.tableName);
		if (filter != null) {
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}
		cmd.text.append(")");
		return cmd;
	}

	protected DatabaseCommand buildSelectCount(EntityAspect aspect, EntityFilter filter) {
		DatabaseCommand cmd = new DatabaseCommand(155);
		cmd.text.append("SELECT COUNT(*) FROM ");
		appendName(cmd.text, aspect.tableName);
		if (filter != null) {
			cmd.text.append(" WHERE ");
			appendFilter(cmd, filter);
		}
		return cmd;
	}

}
