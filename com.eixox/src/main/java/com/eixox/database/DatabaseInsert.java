package com.eixox.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.data.Insert;
import com.eixox.data.InsertRow;

public class DatabaseInsert extends Insert {

	private static final long serialVersionUID = -7695898280237497522L;
	public final Database database;
	public final String tableName;

	public DatabaseInsert(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	public void executeWithKeys(Connection conn) throws SQLException {
		int s = size();
		for (int i = 0; i < s; i++) {
			final InsertRow row = this.get(i);
			DatabaseCommand cmd = this.database.createCommand()
					.appendSql("INSERT INTO ")
					.appendName(tableName)
					.appendSql(" (")
					.appendNames(this.columns)
					.appendSql(") VALUES ");
			appendValues(cmd, row.values);
			cmd.executeInsert(conn, new DatabaseCommandProcessor<Object>() {
				public Object process(ResultSet rs) throws SQLException {
					row.generatedKey = rs.next() ? rs.getObject(1) : null;
					return null;
				}
			});
		}
	}

	private void appendValues(DatabaseCommand cmd, Object[] values) {
		cmd.appendSql("(?");
		cmd.params.add(values[0]);
		for (int i = 1; i < values.length; i++) {
			cmd.appendSql(", ?");
			cmd.params.add(values[i]);
		}
		cmd.appendSql(")");
	}

	public void executeNoKeys(Connection conn) throws SQLException {
		DatabaseCommand cmd = this.database.createCommand()
				.appendSql("INSERT INTO ")
				.appendName(tableName)
				.appendSql(" (")
				.appendNames(this.columns)
				.appendSql(") VALUES ");

		int s = size();
		appendValues(cmd, this.get(0).values);

		for (int i = 1; i < s; i++) {
			cmd.appendSql(", ");
			appendValues(cmd, this.get(i).values);
		}

		cmd.executeUpsert(conn);
	}

	public void execute(Connection conn, boolean returningGeneratedKey) throws SQLException {
		if (returningGeneratedKey)
			executeWithKeys(conn);
		else
			executeNoKeys(conn);
	}

	@Override
	public void execute(boolean includeGeneratedKeys) {
		try {
			Connection conn = this.database.createConnection();
			try {
				execute(conn, includeGeneratedKeys);
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
