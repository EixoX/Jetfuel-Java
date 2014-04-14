package com.eixox.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Iterator;

import com.eixox.data.DataAspect;

public final class ResultSetToClassIterator<T> implements Iterable<T>, Iterator<T> {

	private final ResultSet		rs;
	private final int			columnCount;
	private final int[]			ordinals;
	private final DataAspect	aspect;
	private boolean				next;

	public ResultSetToClassIterator(ResultSet rs, DataAspect aspect) {
		try {
			this.rs = rs;
			this.aspect = aspect;
			this.next = rs.next();
			if (this.next) {
				ResultSetMetaData metaData = rs.getMetaData();
				this.columnCount = metaData.getColumnCount();
				this.ordinals = new int[columnCount];
				for (int i = 0; i < columnCount; i++)
					this.ordinals[i] = aspect.getColumnOrdinal(metaData.getColumnName(i + 1));
			}
			else {
				this.columnCount = -1;
				this.ordinals = null;
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final boolean hasNext() {
		return this.next;
	}

	@SuppressWarnings("unchecked")
	public final T next() {
		try {
			T instance = (T) aspect.newInstance();
			for (int i = 0; i < columnCount; i++)
			{
				if (ordinals[i] >= 0) {
					Object value = rs.getObject(i + 1);
					aspect.get(ordinals[i]).setValue(instance, value);
				}
			}
			this.next = rs.next();
			return instance;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void remove() {
		throw new RuntimeException("You cannot remove from a result set iterator");
	}

	public final Iterator<T> iterator() {
		return this;
	}

}
