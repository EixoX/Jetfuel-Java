package com.eixox.data.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.eixox.data.ClassDelete;
import com.eixox.data.ClassStorage;
import com.eixox.data.FilterExpression;

public class SqlClassDelete extends ClassDelete {

	private final SqlClassStorage storage;

	public SqlClassDelete(SqlClassStorage storage) {
		super(storage);
		this.storage = storage;
	}

	@Override
	protected synchronized long execute(ClassStorage storage, FilterExpression filter) {

		SqlDatabase database = this.storage.getDatabase();

		StringBuilder builder = new StringBuilder(255);
		builder.append("DELETE FROM ");
		builder.append(database.formatTableName(storage.getTableName()));
		if (filter != null) {
			builder.append(" WHERE ");
			
		}

		try {
			Connection connection = database.getConnection();
			try {
				Statement stmt = connection.createStatement();
				try {
					return stmt.executeUpdate(builder.toString());
				} finally {
					stmt.close();
				}
			} finally {
				connection.close();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}

	}

}
