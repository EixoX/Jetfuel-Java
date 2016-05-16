package com.eixox.database;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

public abstract class Database {

	public Driver driver;
	public String url;
	public Properties properties;

	public abstract String getDriverName();

	public abstract char getNamePrefix();

	public abstract char getNameSuffix();

	public abstract boolean supportsTOP();

	public abstract boolean supportsOFFSET();

	public abstract boolean supportsLIMIT();

	public Database(String url, Properties properties)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		String driverName = getDriverName();
		this.driver = (Driver) Class.forName(driverName).newInstance();
		this.url = url;
		this.properties = properties;
	}

	public final Connection createConnection() throws SQLException {
		return this.driver.connect(this.url, this.properties);
	}

	public final DatabaseCommand createCommand() {
		return new DatabaseCommand(this);
	}

}
