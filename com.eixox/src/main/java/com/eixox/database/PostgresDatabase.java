package com.eixox.database;

import java.sql.SQLException;
import java.util.Properties;

public class PostgresDatabase extends Database {

	public PostgresDatabase(String url, Properties properties) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
		super(url, properties);
	}

	@Override
	public final String getDriverName() {
		return "org.postgresql.Driver";
	}

	@Override
	public final char getNamePrefix() {
		return '"';
	}

	@Override
	public final char getNameSuffix() {
		return '"';
	}

	@Override
	public final boolean supportsTOP() {
		return false;
	}

	@Override
	public final boolean supportsOFFSET() {
		return true;
	}

	@Override
	public final boolean supportsLIMIT() {
		return true;
	}

}
