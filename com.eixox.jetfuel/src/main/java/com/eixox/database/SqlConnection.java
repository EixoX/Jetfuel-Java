package com.eixox.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class SqlConnection {

	private final Class<?> driverClass;
	private final String url;
	private final String username;
	private final String password;
	private final Properties properties;

	// _____________________________________________________________________________________________________
	public SqlConnection(String driverName, String url, String username, String password) throws ClassNotFoundException {
		this.driverClass = Class.forName(driverName);
		this.url = url;
		this.username = username;
		this.password = password;
		this.properties = new Properties();
	}

	// _____________________________________________________________________________________________________
	public SqlConnection(String driverName, String url, Properties properties) throws ClassNotFoundException {
		this.driverClass = Class.forName(driverName);
		this.url = url;
		this.username = null;
		this.password = null;
		this.properties = properties;
	}

	// _____________________________________________________________________________________________________
	public final Class<?> getDriverClass() {
		return driverClass;
	}

	// _____________________________________________________________________________________________________
	public final String getUrl() {
		return url;
	}

	// _____________________________________________________________________________________________________
	public final String getUsername() {
		return username;
	}

	// _____________________________________________________________________________________________________
	public final String getPassword() {
		return password;
	}

	// _____________________________________________________________________________________________________
	public final Properties getProperties() {
		return properties;
	}

	// _____________________________________________________________________________________________________
	public final Connection getConnection() throws SQLException {
		if (this.username != null)
			return DriverManager.getConnection(this.url, this.username, this.password);
		else
			return DriverManager.getConnection(this.url, this.properties);
	}

	// _____________________________________________________________________________________________________
	public final SqlCommand createCommand() {
		return new SqlCommand(this);
	}

	// _____________________________________________________________________________________________________
	public final int executeNonQueryText(String commandText) throws SQLException {

		Connection connection = getConnection();
		try {

			Statement createStatement = connection.createStatement();
			try {
				return createStatement.executeUpdate(commandText);
			} finally {
				createStatement.close();
			}
		} finally {
			connection.close();
		}

	}

	// _____________________________________________________________________________________________________
	public final int[] executeNonQueryBatch(List<String> commandTexts) throws SQLException {

		Connection connection = getConnection();
		try {

			Statement createStatement = connection.createStatement();
			try {
				for (String cmd : commandTexts)
					createStatement.addBatch(cmd);

				return createStatement.executeBatch();

			} finally {
				createStatement.close();
			}
		} finally {
			connection.close();
		}

	}

	// _____________________________________________________________________________________________________
	public final int executeNonQueryProc(String commandName, Object... parameters) throws SQLException {

		Connection connection = getConnection();
		try {

			CallableStatement prepareCall = connection.prepareCall(commandName);
			try {

				for (int i = 0; i < parameters.length; i++)
					prepareCall.setObject(i + 1, parameters[i]);

				return prepareCall.executeUpdate();

			} finally {
				prepareCall.close();
			}

		} finally {
			connection.close();
		}

	}
}
