package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityInsert;

public final class DatabaseEntityInsert extends EntityInsert {

	public final Database database;

	public DatabaseEntityInsert(EntityAspect aspect, Database database) {
		super(aspect);
		this.database = database;
	}

	@Override
	public final boolean execute() {
		final DatabaseCommand insert = this.database.buildInsertCommand(this.aspect, this.values, false);
		try {
			Connection conn = database.getConnection();
			try {
				return insert.executeNonQuery(conn) > 0;
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final Object executeAndScopeIdentity() {
		final DatabaseCommand insert = this.database.buildInsertCommand(this.aspect, this.values, true);
		try {
			Connection conn = database.getConnection();
			try {
				return insert.executeScopeIdentity(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
