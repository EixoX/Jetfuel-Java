package com.eixox.database;

import java.sql.SQLException;
import java.util.Properties;

public class MySqlDatabase extends Database {

	public MySqlDatabase(String url, Properties properties)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		super(url, properties);
	}

	@Override
	public String getDriverName() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	public char getNamePrefix() {
		return '`';
	}

	@Override
	public char getNameSuffix() {
		return '`';
	}

	@Override
	public boolean supportsTOP() {
		return false;
	}

	@Override
	public boolean supportsOFFSET() {
		return true;
	}

	@Override
	public boolean supportsLIMIT() {
		return true;
	}

}
