package com.eixox.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.eixox.data.Delete;

public class DatabaseDelete extends Delete {

	public final Database database;
	public final String tableName;

	public DatabaseDelete(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	public long execute(Connection conn) throws SQLException {
		return this.database.createCommand()
				.appendSql("DELETE FROM ")
				.appendName(tableName)
				.appendWhere(where)
				.executeUpsert(conn);
	}

	@Override
	public long execute() {
		try {
			Connection conn = database.createConnection();
			try {
				return execute(conn);
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
