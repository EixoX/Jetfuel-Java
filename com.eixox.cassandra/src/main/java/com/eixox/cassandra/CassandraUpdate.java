package com.eixox.cassandra;

import com.datastax.driver.core.Session;
import com.eixox.data.Update;
import com.eixox.data.UpdateTerm;

public class CassandraUpdate extends Update {

	public final Session session;

	public CassandraUpdate(Session session, String target) {
		super(target);
		this.session = session;
	}

	@Override
	public long execute() {
		int termCount = this.values.size();
		if (termCount < 1)
			return -1;

		UpdateTerm term = values.get(0);

		CassandraCommand cmd = new CassandraCommand(session);
		cmd
				.appendRaw("UPDATE ")
				.appendRaw(this.target)
				.appendRaw(" SET ")
				.appendRaw(term.name)
				.appendRaw("=")
				.appendValue(term.value);

		for (int i = 1; i < termCount; i++)
			cmd.appendRaw(", ")
					.appendRaw(term.name)
					.appendRaw("=")
					.appendValue(term.value);

		cmd.appendWhere(filter);
		cmd.execute();
		return -1L;

	}

}
