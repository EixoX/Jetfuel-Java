package com.eixox.database;

import java.util.Properties;

public class PostgresDatabase extends Database {

	public final PostgresDialect dialect = new PostgresDialect();

	public PostgresDatabase(String url) {
		super(url);
	}

	public PostgresDatabase(String url, Properties properties) {
		super(url, properties);
	}

	@Override
	public final String getDriverClassName() {
		return "org.postgresql.Driver";
	}

	@Override
	protected DatabaseDialect createDialect() {
		return new PostgresDialect();
	}

}
