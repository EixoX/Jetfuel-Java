package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.DataDelete;

public class DatabaseDelete extends DataDelete {
	public final Database database;

	public DatabaseDelete(Database database, String from) {
		super(from);
		this.database = database;
	}

	@Override
	public final long execute() {
		DatabaseCommand cmd = database.dialect.buildDeleteCommand(this.from, this.filter);
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
