package com.eixox.cassandra;

import com.datastax.driver.core.Session;
import com.eixox.data.ColumnType;
import com.eixox.data.entities.DataAspectMember;
import com.eixox.data.entities.EntitySelect;
import com.eixox.data.entities.EntityStorage;

public class CassandraEntityStorage<T> extends EntityStorage<T> {

	public final Session session;

	public CassandraEntityStorage(Session session, CassandraAspect<T> aspect) {
		super(aspect);
		this.session = session;
	}

	public CassandraEntityStorage(Session session, Class<T> claz) {
		super(CassandraAspect.getInstance(claz));
		this.session = session;
	}

	@Override
	public synchronized final long deleteByCompositeKey(Object... keys) {
		CassandraCommand cmd = new CassandraCommand(session);
		cmd
				.appendRaw("DELETE FROM ")
				.appendRaw(aspect.tableName)
				.appendRaw(" WHERE ")
				.appendRaw(aspect.get(aspect.compositeKeyOrdinals[0]).columnName)
				.appendRaw("=")
				.appendValue(keys[0]);

		for (int i = 1; i < aspect.compositeKeyOrdinals.length; i++)
			cmd.appendRaw(" AND ")
					.appendRaw(aspect.get(aspect.compositeKeyOrdinals[i]).columnName)
					.appendRaw("=")
					.appendValue(keys[i]);

		cmd.execute();
		return -1L;
	}

	@Override
	protected synchronized final long deleteByMember(int ordinal, Object value) {
		CassandraCommand cmd = new CassandraCommand(session);
		cmd
				.appendRaw("DELETE FROM ")
				.appendRaw(aspect.tableName)
				.appendRaw(" WHERE ")
				.appendRaw(aspect.get(ordinal).columnName)
				.appendRaw("=")
				.appendValue(value);

		cmd.execute();
		return -1L;
	}

	@Override
	public final EntitySelect<T> select() {
		return new CassandraEntitySelect<T>(session, aspect);
	}

	@Override
	public final synchronized long updateByCompositeKeys(T entity, Object... keys) {
		CassandraCommand cmd = new CassandraCommand(session);

		boolean prependComma = false;
		cmd
				.appendRaw("UPDATE ")
				.appendRaw(aspect.tableName)
				.appendRaw(" SET ");

		for (DataAspectMember member : aspect.members)
			if (member.isReadonly == false)
				if (member.columnType != ColumnType.IDENTITY)
					if (member.columnType != ColumnType.COMPOSITE_KEY) {
						if (prependComma)
							cmd.appendRaw(", ");
						else
							prependComma = true;

						cmd
								.appendRaw(member.columnName)
								.appendRaw("=")
								.appendValue(member.getValue(entity));
					}
		cmd
				.appendRaw(" WHERE ")
				.appendRaw(aspect.get(aspect.compositeKeyOrdinals[0]).columnName)
				.appendRaw("=")
				.appendValue(keys[0]);

		cmd.execute();
		return -1L;
	}

	@Override
	protected synchronized final long updateByMember(T entity, int ordinal, Object value) {
		CassandraCommand cmd = new CassandraCommand(session);
		int colCount = aspect.getColumnCount();
		boolean prependComma = false;
		cmd
				.appendRaw("UPDATE ")
				.appendRaw(aspect.tableName)
				.appendRaw(" SET ");

		for (int i = 0; i < colCount; i++)
			if (i != ordinal) {
				DataAspectMember member = aspect.get(i);
				if (member.isReadonly == false)
					if (member.columnType != ColumnType.IDENTITY) {
						if (prependComma)
							cmd.appendRaw(", ");
						else
							prependComma = true;

						cmd
								.appendRaw(member.columnName)
								.appendRaw("=")
								.appendValue(member.getValue(entity));
					}

			}

		cmd
				.appendRaw(" WHERE ")
				.appendRaw(aspect.get(ordinal).columnName)
				.appendRaw("=")
				.appendValue(value);

		cmd.execute();
		return -1L;
	}

	@Override
	public synchronized final void insert(T entity) {

		int colCount = aspect.members.size();
		CassandraCommand cmd = new CassandraCommand(session)
				.appendRaw("INSERT INTO ")
				.appendRaw(aspect.tableName)
				.appendRaw(" (")
				.appendRaw(aspect.get(0).columnName);

		for (int i = 1; i < colCount; i++)
			cmd.appendRaw(", ").appendRaw(aspect.get(i).columnName);

		cmd
				.appendRaw(") VALUES (")
				.appendValue(aspect.get(0).getValue(entity));

		for (int i = 1; i < colCount; i++)
			cmd
					.appendRaw(", ")
					.appendValue(aspect.get(i).getValue(entity));

		cmd.appendRaw(");");

		try {
			cmd.execute();
		} catch (Exception ex) {
			System.out.println(cmd.text);
			throw new RuntimeException("Ops, não foi possível inserir.", ex);
		}
	}

}
