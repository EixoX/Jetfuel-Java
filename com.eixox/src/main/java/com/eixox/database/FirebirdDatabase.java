package com.eixox.database;

import java.util.Properties;

import com.eixox.database.schema.SchemaDb;

public class FirebirdDatabase extends Database {

	public FirebirdDatabase(String url) {
		super(url);
	}

	public FirebirdDatabase(String url, Properties props) {
		super(url, props);
	}

	@Override
	public String getDriverClassName() {
		return "org.firebirdsql.jdbc.FBDriver";
	}

	@Override
	protected DatabaseDialect createDialect() {
		return new FirebirdDialect();
	}

	@Override
	protected SchemaDb readSchema() {
		throw new RuntimeException("THIS IS NOT YET IMPLEMENTED FOR THIS DATABASE");
	}

}
