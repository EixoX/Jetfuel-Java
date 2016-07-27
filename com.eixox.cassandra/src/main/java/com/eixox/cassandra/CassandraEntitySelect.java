package com.eixox.cassandra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.eixox.data.entities.DataAspect;
import com.eixox.data.entities.EntitySelect;

public class CassandraEntitySelect<T> extends EntitySelect<T> {

	public final Session session;

	public CassandraEntitySelect(Session session, DataAspect<T, ?> aspect) {
		super(aspect);
		this.session = session;
	}

	@Override
	public final long count() {
		return new CassandraCommand(session)
				.appendRaw("SELECT COUNT(*) as c FROM ")
				.appendRaw(aspect.tableName)
				.appendWhere(filter)
				.execute(new CassandraCommandProcessor<Long>() {
					public Long process(ResultSet rs) {
						Row one = rs.one();
						return one == null ? 0L : one.getLong("c");
					}
				});
	}

	@Override
	public final boolean exists() {
		return new CassandraCommand(session)
				.appendRaw("SELECT 1 as one FROM ")
				.appendRaw(aspect.tableName)
				.appendWhere(filter)
				.appendLimit(1)
				.execute(new CassandraCommandProcessor<Boolean>() {
					public Boolean process(ResultSet rs) {
						Row one = rs.one();
						return one == null ? false : one.getInt("one") == 1;
					}
				});
	}

	@Override
	public List<T> execute() {
		return new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendColumns(aspect.members)
				.appendRaw(" FROM ")
				.appendRaw(aspect.tableName)
				.appendWhere(filter)
				.appendLimit(this.offset + this.limit)
				.execute(new CassandraCommandProcessor<List<T>>() {
					public List<T> process(ResultSet rs) {

						Iterator<Row> iterator = rs.iterator();
						ArrayList<T> list = limit > 0 ? new ArrayList<T>(limit) : new ArrayList<T>();
						int colCount = aspect.getColumnCount();

						// offsets
						for (int i = 0; i < offset && iterator.hasNext(); i++)
							iterator.next();

						while (iterator.hasNext()) {
							Row row = iterator.next();
							T entity = aspect.newInstance();
							for (int i = 0; i < colCount; i++) {
								Object val = row.getObject(i);
								if (val != null)
									aspect.setValue(entity, i, val);
							}
							list.add(entity);
						}

						return list;

					}
				});
	}

	@Override
	public T first() {
		return new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendColumns(aspect.members)
				.appendRaw(" FROM ")
				.appendRaw(aspect.tableName)
				.appendWhere(filter)
				.appendLimit(1)
				.execute(new CassandraCommandProcessor<T>() {
					public T process(ResultSet rs) {

						Iterator<Row> iterator = rs.iterator();

						// offsets
						for (int i = 0; i < offset && iterator.hasNext(); i++)
							iterator.next();

						if (!iterator.hasNext())
							return null;

						Row one = iterator.next();
						int s = aspect.getColumnCount();
						T entity = aspect.newInstance();
						for (int i = 0; i < s; i++) {
							Object val = one.getObject(i);
							if (val != null)
								aspect.setValue(entity, i, val);
						}
						return entity;
					}
				});
	}

	@Override
	public Object first(final int ordinal) {
		return new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendColumns(aspect.members)
				.appendRaw(" FROM ")
				.appendRaw(aspect.tableName)
				.appendWhere(filter)
				.appendLimit(1)
				.execute(new CassandraCommandProcessor<Object>() {
					public Object process(ResultSet rs) {
						Iterator<Row> iterator = rs.iterator();

						// offsets
						for (int i = 0; i < offset && iterator.hasNext(); i++)
							iterator.next();

						if (!iterator.hasNext())
							return null;

						Row one = iterator.next();
						return one.getObject(aspect.get(ordinal).columnName);
					}
				});
	}

}
