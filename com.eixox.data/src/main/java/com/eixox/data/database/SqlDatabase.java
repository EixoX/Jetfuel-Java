package com.eixox.data.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlDatabase {

	private final String connectionString;
	private final String username;
	private final String password;

	public SqlDatabase(String connectionString, String username, String password) {
		this.connectionString = connectionString;
		this.username = username;
		this.password = password;
	}

	// Description Here:
	// _____________________________________________________
	public final Connection getConnection() throws SQLException {
		if (this.username == null) {
			return DriverManager.getConnection(this.connectionString);
		} else {
			return DriverManager.getConnection(this.connectionString, this.username, this.password);
		}
	}

	// Description Here:
	// _____________________________________________________
	public final int executeNonQuery(String command) throws SQLException {
		Connection conn = getConnection();
		try {
			Statement stmt = conn.createStatement();
			try {
				return stmt.executeUpdate(command);
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public final int executeNonQuery(String command, Object... parameters) throws SQLException {
		final Connection conn = getConnection();
		try {
			final CallableStatement call = conn.prepareCall(command);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);
				return call.executeUpdate();
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public final Object executeScalar(String command) throws SQLException {
		final Connection conn = getConnection();
		try {
			final Statement stmt = conn.createStatement();
			try {
				final ResultSet rs = stmt.executeQuery(command);
				try {
					if (rs.next())
						return rs.getObject(1);
					else
						return null;
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public final Object executeScalar(String command, Object... parameters) throws SQLException {
		final Connection conn = getConnection();
		try {
			final CallableStatement call = conn.prepareCall(command);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);

				final ResultSet rs = call.executeQuery();
				try {
					if (rs.next())
						return rs.getObject(1);
					else
						return null;
				} finally {
					rs.close();
				}

			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

}
