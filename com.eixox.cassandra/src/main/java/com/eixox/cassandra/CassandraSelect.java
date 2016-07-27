package com.eixox.cassandra;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.eixox.data.Select;
import com.eixox.data.entities.DataAspect;

public class CassandraSelect extends Select {

	public final Session session;

	public CassandraSelect(Session session, String from) {
		super(from);
		this.session = session;
	}

	@Override
	public long count() {
		CassandraCommand cmd = new CassandraCommand(session);
		cmd.appendRaw("SELECT COUNT(*) as counter FROM ").appendRaw(from).appendWhere(filter);
		return cmd.execute(new CassandraCommandProcessor<Long>() {
			public Long process(ResultSet rs) {
				Row row = rs.one();
				return row == null ? 0L : row.getLong("counter");
			}
		});
	}

	@Override
	public boolean exists() {
		return count() > 0;
	}

	@Override
	public List<Object[]> execute() {
		CassandraCommand cmd = new CassandraCommand(session);
		cmd.appendRaw("SELECT ");

		final int colCount = columns.size();
		if (colCount == 0)
			cmd.appendRaw("*");
		else {
			cmd.appendRaw(columns.get(0));
			for (int i = 1; i < colCount; i++)
				cmd
						.appendRaw(", ")
						.appendRaw(columns.get(i));
		}

		// calculate *top
		cmd
				.appendRaw(" FROM ")
				.appendRaw(from)
				.appendWhere(filter)
				.appendSort(sort)
				.appendLimit(this.offset + this.limit);

		return cmd.execute(new CassandraCommandProcessor<List<Object[]>>() {
			public List<Object[]> process(ResultSet rs) {

				Iterator<Row> iter = rs.iterator();

				// offsets
				if (offset > 0)
					for (int i = 0; i < offset && iter.hasNext(); i++)
						iter.next();

				// col count
				int ncolcount = colCount == 0 ? rs.getColumnDefinitions().size() : colCount;

				ArrayList<Object[]> list = new ArrayList<Object[]>();
				while (iter.hasNext()) {
					Row next = iter.next();
					Object[] r = new Object[ncolcount];
					for (int i = 0; i < ncolcount; i++)
						r[i] = next.getObject(i);
					list.add(r);
				}
				return list;

			}
		});
	}

	@Override
	public Object[] first() {
		CassandraCommand cmd = new CassandraCommand(session);
		cmd.appendRaw("SELECT ");

		final int colCount = columns.size();
		if (colCount == 0)
			cmd.appendRaw("*");
		else {
			cmd.appendNames(columns);
		}

		// calculate *top
		cmd
				.appendRaw(" FROM ")
				.appendRaw(from)
				.appendWhere(filter)
				.appendSort(sort)
				.appendLimit(1);

		return cmd.execute(new CassandraCommandProcessor<Object[]>() {
			public Object[] process(ResultSet rs) {
				Row next = rs.one();
				if (next == null)
					return null;

				int ccount = colCount > 0 ? colCount : rs.getColumnDefinitions().size();
				Object[] row = new Object[ccount];
				for (int i = 0; i < row.length; i++)
					row[i] = next.getObject(i);
				return row;
			}
		});

	}

	@Override
	public Object first(String columnName) {
		CassandraCommand cmd = new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendRaw(columnName)
				.appendRaw(" FROM ")
				.appendRaw(from)
				.appendWhere(filter)
				.appendSort(sort)
				.appendLimit(1);

		ResultSet rs = cmd.execute();
		Row one = rs.one();
		return one == null ? null : one.getObject(0);
	}

	@Override
	public <T> List<T> execute(DataAspect<T, ?> aspect) {
		CassandraCommand cmd = new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendNames(columns)
				.appendRaw(" FROM ")
				.appendRaw(from)
				.appendWhere(filter)
				.appendSort(sort)
				.appendLimit(this.offset + this.limit);

		ResultSet rs = cmd.execute();
		int colCount = columns.size();
		List<T> list = new ArrayList<T>();
		Iterator<Row> iter = rs.iterator();

		// offset
		for (int i = 0; i < offset && iter.hasNext(); i++)
			iter.next();

		while (iter.hasNext()) {
			T entity = aspect.newInstance();
			for (int i = 0; i < colCount; i++) {
				Row row = iter.next();
				Object val = row.getObject(i);
				if (val != null)
					aspect.setValue(entity, i, val);
				list.add(entity);
			}
		}
		return list;
	}

	@Override
	public <T> T first(DataAspect<T, ?> aspect) {
		CassandraCommand cmd = new CassandraCommand(session)
				.appendRaw("SELECT ")
				.appendNames(columns)
				.appendRaw(" FROM ")
				.appendRaw(from)
				.appendWhere(filter)
				.appendSort(sort)
				.appendLimit(this.offset + this.limit);

		ResultSet rs = cmd.execute();
		int colCount = columns.size();
		Iterator<Row> iter = rs.iterator();

		// offset
		for (int i = 0; i < offset && iter.hasNext(); i++)
			iter.next();

		if (!iter.hasNext())
			return null;

		T entity = aspect.newInstance();
		for (int i = 0; i < colCount; i++) {
			Row row = iter.next();
			Object val = row.getObject(i);
			if (val != null)
				aspect.setValue(entity, i, val);
		}
		return entity;
	}

}
