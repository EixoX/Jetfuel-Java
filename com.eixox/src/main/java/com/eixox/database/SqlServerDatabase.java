package com.eixox.database;

import java.util.Properties;

public class SqlServerDatabase extends Database {

	public final SqlServerDialect dialect = new SqlServerDialect();

	public SqlServerDatabase(String url) {
		super(url);
	}

	public SqlServerDatabase(String url, Properties properties) {
		super(url, properties);
	}

	@Override
	public final String getDriverClassName() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected DatabaseDialect createDialect() {
		return new SqlServerDialect();
	}

}
