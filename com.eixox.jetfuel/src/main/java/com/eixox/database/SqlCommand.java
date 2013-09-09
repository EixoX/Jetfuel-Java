package com.eixox.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.eixox.data.ClassStorage;

public class SqlCommand {

	private SqlConnection connection;
	private final StringBuilder command;
	private final ArrayList<Object> values;

	public SqlCommand() {
		this.command = new StringBuilder(128);
		this.values = new ArrayList<Object>();
	}

	public SqlCommand(SqlConnection connection) {
		this();
		this.connection = connection;
	}

	public final SqlConnection getConnection() {
		return this.connection;
	}

	public final void setConnection(SqlConnection connection) {
		this.connection = connection;
	}

	public final void append(String text) {
		this.command.append(text);
	}

	public final void append(int value) {
		this.command.append(value);
	}

	public final ArrayList<Object> getValues() {
		return this.values;
	}

	public final void addValue(Object value) {
		this.values.add(value);
	}

	public final String getText() {
		return this.command.toString();
	}

	public final int executeNonQuery() throws SQLException {
		Connection conn = this.connection.getConnection();
		try {
			return executeNonQuery(conn);
		} finally {
			conn.close();
		}
	}

	public final int executeNonQuery(Connection conn) throws SQLException {
		int count = this.values.size();
		if (count == 0) {
			Statement createStatement = conn.createStatement();
			try {
				return createStatement.executeUpdate(this.command.toString());
			} finally {
				createStatement.close();
			}
		} else {
			CallableStatement prepareCall = conn.prepareCall(this.command.toString());
			for (int i = 0; i < count; i++)
				prepareCall.setObject(i + 1, this.values.get(i));
			try {
				return prepareCall.executeUpdate();
			} finally {
				prepareCall.close();
			}
		}
	}

	public final Object executeScalar() throws SQLException {
		Connection conn = this.connection.getConnection();
		try {
			return executeScalar(conn);
		} finally {
			conn.close();
		}
	}

	public final Object executeScalar(Connection conn) throws SQLException {
		int count = this.values.size();
		if (count == 0) {
			Statement createStatement = conn.createStatement();
			try {
				ResultSet rs = createStatement.executeQuery(this.command.toString());
				try {
					return rs.next() ? rs.getObject(1) : null;
				} finally {
					rs.close();
				}
			} finally {
				createStatement.close();
			}
		} else {
			CallableStatement prepareCall = conn.prepareCall(this.command.toString());
			for (int i = 0; i < count; i++)
				prepareCall.setObject(i + 1, this.values.get(i));
			try {
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
	}

	public final Object executeGenerated() throws SQLException {
		Connection conn = this.connection.getConnection();
		try {
			return executeGenerated(conn);
		} finally {
			conn.close();
		}
	}

	public final Object executeGenerated(Connection conn) throws SQLException {
		int count = this.values.size();
		if (count == 0) {
			Statement createStatement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, Statement.RETURN_GENERATED_KEYS);
			try {
				createStatement.executeUpdate(this.command.toString());
				ResultSet rs = createStatement.getGeneratedKeys();
				try {
					return rs.next() ? rs.getObject(1) : null;
				} finally {
					rs.close();
				}
			} finally {
				createStatement.close();
			}
		} else {
			CallableStatement prepareCall = conn.prepareCall(this.command.toString(), ResultSet.TYPE_FORWARD_ONLY, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < count; i++)
				prepareCall.setObject(i + 1, this.values.get(i));
			try {
				prepareCall.executeUpdate();
				ResultSet rs = prepareCall.getGeneratedKeys();
				try {
					return rs.next() ? rs.getObject(1) : null;
				} finally {
					rs.close();
				}
			} finally {
				prepareCall.close();
			}
		}
	}

	public final <T> T executeQuery(ResultsetProcessor<T> processor) throws SQLException {
		Connection conn = this.connection.getConnection();
		try {
			return executeQuery(conn, processor);
		} finally {
			conn.close();
		}
	}

	public final <T> T executeQuery(Connection conn, ResultsetProcessor<T> processor) throws SQLException {
		int count = this.values.size();
		if (count == 0) {
			Statement createStatement = conn.createStatement();
			try {
				ResultSet rs = createStatement.executeQuery(this.command.toString());
				try {
					return processor.process(rs);
				} finally {
					rs.close();
				}
			} finally {
				createStatement.close();
			}
		} else {
			CallableStatement prepareCall = conn.prepareCall(this.command.toString());
			for (int i = 0; i < count; i++)
				prepareCall.setObject(i + 1, this.values.get(i));
			try {
				ResultSet rs = prepareCall.executeQuery();
				try {
					return processor.process(rs);
				} finally {
					rs.close();
				}
			} finally {
				prepareCall.close();
			}
		}
	}

	private final Object build(ResultSet resultSet, ClassStorage<?> aspect) throws SQLException, InstantiationException, IllegalAccessException {
		Object instance = aspect.newInstance();
		int count = aspect.getCount();
		for (int i = 0; i < count; i++) {
			Object o = resultSet.getObject(i + 1);
			if (o != null)
				aspect.setValue(instance, i, o);
		}
		return instance;
	}

	public final Object executeScalar(ClassStorage<?> aspect) throws InstantiationException, IllegalAccessException, SQLException {
		Connection conn = this.connection.getConnection();
		try {
			return executeScalar(conn, aspect);
		} finally {
			conn.close();
		}
	}

	public final Object executeScalar(Connection conn, ClassStorage<?> aspect) throws SQLException, InstantiationException, IllegalAccessException {
		int count = this.values.size();
		if (count == 0) {
			Statement createStatement = conn.createStatement();
			try {
				ResultSet rs = createStatement.executeQuery(this.command.toString());
				try {
					return rs.next() ? build(rs, aspect) : null;
				} finally {
					rs.close();
				}
			} finally {
				createStatement.close();
			}
		} else {
			CallableStatement prepareCall = conn.prepareCall(this.command.toString());
			for (int i = 0; i < count; i++)
				prepareCall.setObject(i + 1, this.values.get(i));
			try {
				ResultSet rs = prepareCall.executeQuery();
				try {
					return rs.next() ? build(rs, aspect) : null;
				} finally {
					rs.close();
				}
			} finally {
				prepareCall.close();
			}
		}
	}
}
