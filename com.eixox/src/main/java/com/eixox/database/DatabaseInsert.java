package com.eixox.database;

import java.sql.Connection;
import java.util.List;

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

		long counter = 0;

		int imax = this.size();
		for (int i = 0; i < imax; i += 50) {
			int end = i + 50;
			if (end > imax)
				end = imax;

			List<Object[]> subList = this.subList(i, end);
			DatabaseCommand cmd = database.dialect.buildInsertCommand(this.from, this.cols, subList);
			try {
				Connection conn = database.getConnection();
				try {
					counter += cmd.executeNonQuery(conn);
				} finally {
					conn.close();
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return counter;

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
