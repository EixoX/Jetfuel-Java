package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.DataInsert;

public class DatabaseInsert extends DataInsert {
	private static final long serialVersionUID = 5745406236862384979L;
	public final Database database;

	public DatabaseInsert(Database database, String from) {
		super(from);
		this.database = database;
	}

	@Override
	public final long execute() {
		DatabaseCommand cmd = database.dialect.buildInsertCommand(this.from, this.cols, this);
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

	@Override
	public Object executeAndScopeIdentity() {
		DatabaseCommand cmd = database.dialect.buildInsertCommand(this.from, this.cols, this);
		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeScopeIdentity(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
