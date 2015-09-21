package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.DataUpdate;

public class DatabaseUpdate extends DataUpdate {

	public final Database database;

	public DatabaseUpdate(Database database, String name) {
		super(name);
		this.database = database;
	}

	@Override
	public final long execute() {

		DatabaseCommand cmd = database.dialect.buildUpdateCommand(this.from, this.values, this.filter);
		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeNonQuery(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
