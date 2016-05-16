package com.eixox.database;

import java.sql.SQLException;
import java.util.Properties;

public class SqlServerDatabase extends Database {

	public SqlServerDatabase(String url, Properties properties)
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {

		super(url, properties);
	}

	@Override
	public String getDriverName() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	public char getNamePrefix() {
		return '[';
	}

	@Override
	public char getNameSuffix() {
		return ']';
	}

	@Override
	public boolean supportsTOP() {
		return true;
	}

	@Override
	public boolean supportsOFFSET() {
		return false;
	}

	@Override
	public boolean supportsLIMIT() {
		return false;
	}

}
