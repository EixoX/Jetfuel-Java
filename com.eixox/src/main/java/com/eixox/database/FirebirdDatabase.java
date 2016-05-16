package com.eixox.database;

import java.sql.SQLException;
import java.util.Properties;

public class FirebirdDatabase extends Database {

	public FirebirdDatabase(String url, Properties props)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		super(url, props);
	}

	@Override
	public String getDriverName() {
		return "org.firebirdsql.jdbc.FBDriver";
	}

	@Override
	public char getNamePrefix() {
		return ' ';
	}

	@Override
	public char getNameSuffix() {
		return ' ';
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
