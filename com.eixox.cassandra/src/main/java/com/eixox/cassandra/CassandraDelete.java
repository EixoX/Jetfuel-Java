package com.eixox.cassandra;

import java.util.ArrayList;

import com.datastax.driver.core.Session;
import com.eixox.data.Delete;

public class CassandraDelete extends Delete {

	public final ArrayList<String> columns = new ArrayList<String>();
	public final Session session;

	public CassandraDelete(Session session, String from) {
		super(from);
		this.session = session;

	}

	public final CassandraDelete addColumn(String... columns) {
		if (columns != null)
			for (int i = 0; i < columns.length; i++)
				this.columns.add(columns[i]);
		return this;
	}

	@Override
	public long execute() {
		CassandraCommand command = new CassandraCommand(session);
		int colSize = this.columns.size();

		command.appendRaw("DELETE ");
		if (colSize > 0) {
			command.appendRaw(this.columns.get(0));
			for (int i = 1; i < colSize; i++) {
				command.appendRaw(", ");
				command.appendRaw(this.columns.get(i));
			}
		}
		command
				.appendRaw(" FROM ")
				.appendRaw(this.from)
				.appendWhere(filter)
				.execute();
		return -1;
	}

}
