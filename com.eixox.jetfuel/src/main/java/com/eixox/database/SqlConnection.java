package com.eixox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class SqlConnection {

	private final Class<?> driverClass;
	private final String url;
	private final String username;
	private final String password;
	private final Properties properties;

	public SqlConnection(String driverName, String url, String username, String password) throws ClassNotFoundException {
		this.driverClass = Class.forName(driverName);
		this.url = url;
		this.username = username;
		this.password = password;
		this.properties = new Properties();
	}

	public SqlConnection(String driverName, String url, Properties properties) throws ClassNotFoundException {
		this.driverClass = Class.forName(driverName);
		this.url = url;
		this.username = null;
		this.password = null;
		this.properties = properties;
	}

	public final Class<?> getDriverClass() {
		return driverClass;
	}

	public final String getUrl() {
		return url;
	}

	public final String getUsername() {
		return username;
	}

	public final String getPassword() {
		return password;
	}

	public final Properties getProperties() {
		return properties;
	}

	public final Connection getConnection() throws SQLException {
		if (this.username != null)
			return DriverManager.getConnection(this.url, this.username, this.password);
		else
			return DriverManager.getConnection(this.url, this.properties);
	}

	public final SqlCommand createCommand() {
		return new SqlCommand(this);
	}
}
