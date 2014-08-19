package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityUpdate;

public class DatabaseEntityUpdate extends EntityUpdate {

	public final Database database;

	public DatabaseEntityUpdate(EntityAspect aspect, Database database) {
		super(aspect);
		this.database = database;
	}

	@Override
	public final long execute() {

		final DatabaseCommand update = this.database.buildUpdateCommand(aspect, values, filter);
		try {
			Connection conn = database.getConnection();
			try {
				return update.executeNonQuery(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
