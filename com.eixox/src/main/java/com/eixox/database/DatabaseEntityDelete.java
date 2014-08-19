package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityDelete;

public final class DatabaseEntityDelete extends EntityDelete {

	public final Database database;

	public DatabaseEntityDelete(EntityAspect aspect, Database database) {
		super(aspect);
		this.database = database;
	}

	@Override
	public final long execute() {
		final DatabaseCommand delete = this.database.buildDeleteCommand(aspect, filter);
		try {
			Connection conn = database.getConnection();
			try {
				return delete.executeNonQuery(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
