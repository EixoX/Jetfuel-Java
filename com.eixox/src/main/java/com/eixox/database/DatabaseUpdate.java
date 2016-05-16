package com.eixox.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.eixox.data.Update;

public class DatabaseUpdate extends Update {

	private static final long serialVersionUID = -779814878323604636L;
	public final Database database;
	public final String tableName;

	public DatabaseUpdate(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	public long execute(Connection conn) throws SQLException {
		DatabaseCommand cmd = this.database.createCommand()
				.appendSql("UPDATE ")
				.appendName(tableName)
				.appendSql(" SET ");

		boolean prependComma = false;
		for (Entry<String, Object> item : this.entrySet()) {
			if (prependComma)
				cmd.appendSql(", ");
			else
				prependComma = true;

			cmd.appendName(item.getKey());
			cmd.text.append("=?");
			cmd.params.add(item.getValue());
		}

		return cmd.appendWhere(where).executeUpsert();
	}

	@Override
	public long execute() {
		try {
			Connection conn = this.database.createConnection();
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
