package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;
import com.eixox.data.entities.EntityInsertBulk;

public class DatabaseEntityInsertBulk<T> extends EntityInsertBulk<T> {

	public final Database database;
	private DatabaseCommand command;

	public DatabaseEntityInsertBulk(EntityAspect aspect, Database database) {
		super(aspect);
		this.database = database;
	}

	@Override
	public void add(T entity) {
		Object[] values = new Object[aspect.getCount()];
		for (int i = 0; i < values.length; i++)
			if (i != aspect.identityOrdinal) {
				EntityAspectMember member = aspect.get(i);
				if (member.readOnly)
					values[i] = Void.class;
				else
					values[i] = member.getValue(entity);
			}
			else
				values[i] = Void.class;

		if (command == null)
			command = database.buildInsertCommand(aspect, values, false);
		else {
			command.text.append(", (");
			boolean prependComma = false;
			for (int i = 0; i < values.length; i++)
				if (values[i] != Void.class) {
					if (prependComma)
						command.text.append(",?");
					else {
						command.text.append("?");
						prependComma = true;
					}

					command.parameters.add(values[i]);
				}
		}
	}

	@Override
	public final int execute() {
		try {
			Connection conn = database.getConnection();
			try {
				return command.executeNonQuery(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
