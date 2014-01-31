package com.eixox.data.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;

public class SqlCommand {

	private final SqlDatabase database;
	private final StringBuilder commandText;
	private final ArrayList<Object> commandParameters;

	public SqlCommand(SqlDatabase database) {
		this.database = database;
		this.commandParameters = new ArrayList<Object>();
		this.commandText = new StringBuilder(255);
	}

	public final SqlCommand appendRaw(String commandText) {
		this.commandText.append(commandText);
		return this;
	}

	public final void appendColumnName(String columnName) {
		this.commandText.append(columnName);
	}

	public void appendFilter(FilterExpression expression) {
		commandText.append("(");
		appendFilter(expression.getFirst());
		commandText.append(")");
	}

	public void appendFilter(FilterNode node) {
		Filter filter = node.getFilter();
		switch (filter.getFilterType()) {
		case Expression:
			appendFilter((FilterExpression) filter);
			break;
		case Term:
			appendFilter((FilterTerm) filter);
			break;
		default:
			throw new RuntimeException("This needs to be either a filter term or a filter expression.");
		}
		FilterNode next = node.getNext();
		if (next != null) {
			switch (node.getOperation()) {
			case And:
				commandText.append(" AND ");
				appendFilter(next);
				break;
			case Or:
				commandText.append(" OR ");
				appendFilter(next);
				break;
			default:
				throw new RuntimeException("Unkown filter operation " + node.getOperation());
			}
		}
	}

	public void appendFilter(FilterTerm filter) {

		appendColumnName(filter.getColumnName());
		Object value = filter.getValue();

		switch (filter.getComparison()) {
		case EqualTo:
			if (value == null)
				this.commandText.append(" IS NULL");
			else {
				this.commandText.append(" = ");
				this.appendParameter(value);
			}
			break;
		case GreaterOrEqual:
			this.commandText.append(" >= ");
			this.appendParameter(value);
			break;
		case GreaterThan:
			this.commandText.append(" > ");
			this.appendParameter(value);
			break;
		case In:
			this.commandText.append(" IN ");
			this.appendParameter(value);
			break;
		case Like:
			this.commandText.append(" LIKE ");
			this.appendParameter(value);
			break;
		case LowerOrEqual:
			this.commandText.append(" <= ");
			this.appendParameter(value);
			break;
		case LowerThan:
			this.commandText.append(" < ");
			this.appendParameter(value);
			break;
		case Not_EqualTo:
			if (value == null)
				this.commandText.append(" IS NOT NULL");
			else {
				this.commandText.append(" != ");
				this.appendParameter(value);
			}
			break;
		case Not_In:
			this.commandText.append(" NOT IN ");
			this.appendParameter(value);
			break;
		case Not_Like:
			this.commandText.append(" NOT LIKE ");
			this.appendParameter(value);
			break;
		default:
			throw new RuntimeException("Unknown filter comparison " + filter.getComparison());

		}

	}

	public final SqlCommand appendParameter(Object value) {
		if (value instanceof Iterable<?>) {
			Iterator<?> it = ((Iterable<?>) value).iterator();
			this.commandText.append("(");
			if (it.hasNext()) {
				appendParameter(it.next());
				do {
					this.commandText.append(", ");
					appendParameter(it.next());
				} while (it.hasNext());
			}
			this.commandText.append(")");
		} else {
			this.commandText.append("? ");
			this.commandParameters.add(value);
		}
		return this;
	}

	public final SqlDatabase getDatabase() {
		return this.database;
	}

	public final int executeNonQuery() throws SQLException {
		Connection conn = this.database.getConnection();
		try {
			int cmdSize = this.commandParameters.size();
			if (cmdSize < 1) {
				Statement createStatement = conn.createStatement();
				try {
					return createStatement.executeUpdate(this.commandText.toString());
				} finally {
					createStatement.close();
				}
			} else {
				CallableStatement prepareCall = conn.prepareCall(this.commandText.toString());
				try {
					for (int i = 0; i < cmdSize; i++)
						prepareCall.setObject(i + 1, this.commandParameters.get(i));
					return prepareCall.executeUpdate();
				} finally {
					prepareCall.close();
				}
			}

		} finally {
			conn.close();
		}
	}

	public final Object executeScalar() throws SQLException {
		Connection conn = this.database.getConnection();
		try {
			int cmdSize = this.commandParameters.size();
			if (cmdSize < 1) {
				Statement createStatement = conn.createStatement();
				try {
					ResultSet rs = createStatement.executeQuery(this.commandText.toString());
					try {
						return rs.next() ? rs.getObject(1) : null;
					} finally {
						rs.close();
					}
				} finally {
					createStatement.close();
				}
			} else {
				CallableStatement prepareCall = conn.prepareCall(this.commandText.toString());
				try {
					for (int i = 0; i < cmdSize; i++)
						prepareCall.setObject(i + 1, this.commandParameters.get(i));
					ResultSet rs = prepareCall.executeQuery();
					try {
						return rs.next() ? rs.getObject(1) : null;
					} finally {
						rs.close();
					}
				} finally {
					prepareCall.close();
				}
			}

		} finally {
			conn.close();
		}
	}
}
