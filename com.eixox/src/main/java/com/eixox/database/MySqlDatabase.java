package com.eixox.database;

import java.util.Properties;

import com.eixox.database.schema.SchemaDb;

public class MySqlDatabase extends Database {

	public final MySqlDialect dialect = new MySqlDialect();

	public MySqlDatabase(String url) {
		super(url);
	}

	public MySqlDatabase(String url, Properties properties) {
		super(url, properties);
	}

	@Override
	public final String getDriverClassName() {
		return "com.mysql.jdbc.Driver";
	}

	@Override
	protected DatabaseDialect createDialect() {
		return new MySqlDialect();
	}

	@Override
	protected SchemaDb readSchema() {
		throw new RuntimeException("THIS IS NOT YET IMPLEMENTED FOR THIS DATABASE");
	}
	

}
