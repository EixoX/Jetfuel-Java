package com.eixox.database;

import java.util.Properties;

import com.eixox.database.schema.SchemaDb;

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
	@Override
	protected SchemaDb readSchema() {
		throw new RuntimeException("THIS IS NOT YET IMPLEMENTED FOR THIS DATABASE");
	}
}
