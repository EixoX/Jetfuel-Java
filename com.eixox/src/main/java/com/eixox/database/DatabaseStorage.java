package com.eixox.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eixox.data.entities.EntityDelete;
import com.eixox.data.entities.EntityInsert;
import com.eixox.data.entities.EntityInsertBulk;
import com.eixox.data.entities.EntitySelect;
import com.eixox.data.entities.EntityStorage;
import com.eixox.data.entities.EntityUpdate;

public class DatabaseStorage<T> extends EntityStorage<T> {

	private final Database database;

	public DatabaseStorage(Database database, Class<T> claz) {
		super(DatabaseAspect.getInstance(claz));
		this.database = database;
	}

	@Override
	public final EntitySelect<T> select() {
		return new DatabaseEntitySelect<T>(aspect, database);
	}

	@Override
	public final EntityDelete delete() {
		return new DatabaseEntityDelete(aspect, database);
	}

	@Override
	public final EntityInsert insert() {
		return new DatabaseEntityInsert(aspect, database);
	}

	@Override
	public final EntityInsertBulk<T> insertBulk() {
		return new DatabaseEntityInsertBulk<T>(aspect, database);
	}

	@Override
	public final EntityUpdate update() {
		return new DatabaseEntityUpdate(aspect, database);
	}

	public final List<T> executeQuery(String commandText, Object... parameters) {
		DatabaseCommand cmd = new DatabaseCommand(commandText.length());
		cmd.text.append(commandText);
		for (int i = 0; i < parameters.length; i++)
			cmd.parameters.add(parameters[i]);

		List<T> list = new ArrayList<T>();
		try {
			Connection conn = database.getConnection();
			try {
				cmd.executeQuery(conn, aspect, list);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
