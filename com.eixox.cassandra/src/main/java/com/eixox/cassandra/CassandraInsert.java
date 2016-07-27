package com.eixox.cassandra;

import com.datastax.driver.core.Session;
import com.eixox.data.Insert;

public class CassandraInsert extends Insert {

	public final Session session;

	public CassandraInsert(Session session, String into) {
		super(into);
		this.session = session;
	}

	@Override
	public long execute() {

		int colCount = columns.size();
		int rowCount = rows.size();

		if (colCount < 1)
			return -2;

		for (int i = 0; i < rowCount; i++) {
			CassandraCommand cmd = new CassandraCommand(session);
			Object[] row = rows.get(i).values;
			cmd
					.appendRaw("INSERT INTO ")
					.appendRaw(into)
					.appendRaw(" (")
					.appendRaw(columns.get(0));

			for (int j = 1; j < colCount; j++)
				cmd.appendRaw(", ")
						.appendRaw(columns.get(j));

			cmd.appendRaw(") VALUES (");
			cmd.appendValue(row[0]);
			for (int j = 1; j < colCount; j++)
				cmd.appendRaw(", ")
						.appendValue(row[j]);
			cmd.appendRaw(");");
			cmd.execute();
		}

		return rows.size();

	}

}
