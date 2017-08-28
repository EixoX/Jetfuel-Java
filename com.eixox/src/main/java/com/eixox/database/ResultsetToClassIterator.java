package com.eixox.database;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import com.eixox.data.entities.EntityAspect;

public final class ResultsetToClassIterator<T> implements Iterable<T>, Iterator<T> {

	private final ResultSet resultSet;
	private final EntityAspect aspect;
	private final int colCount;
	private final int[] mappings;

	public ResultsetToClassIterator(EntityAspect aspect, ResultSet resultSet) throws SQLException {
		this.aspect = aspect;
		this.resultSet = resultSet;

		ResultSetMetaData metadata = resultSet.getMetaData();
		this.colCount = metadata.getColumnCount();
		this.mappings = new int[this.colCount];
		for (int i = 0; i < colCount; i++) {
			String colName = metadata.getColumnName(i + 1);
			mappings[i] = aspect.getColumnOrdinal(colName);
		}

	}

	public final boolean hasNext() {
		try {
			return this.resultSet.next();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public final int skip(int count) {
		int i = 0;
		try {
			for (; i < count && this.resultSet.next(); i++)
				;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return i;
	}

	public final int take(int count, List<T> output) {
		int i = 0;
		try {
			for (; i < count && this.resultSet.next(); i++) {
				T item = next();
				output.add(item);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		return i;
	}

	@SuppressWarnings("unchecked")
	public final T next() {
		try {
			T entity = (T) aspect.newInstance();
			for (int i = 0; i < colCount; i++)
				if (mappings[i] >= 0) {
					Object value = resultSet.getObject(i + 1);
					if (value != null) {
						Class<?> targetType = aspect.get(mappings[i]).getDataType();
						if (targetType == Character.class || targetType == Character.TYPE) {
							String s = (String) value;
							if (!s.isEmpty())
								aspect.setValue(entity, mappings[i], s.charAt(0));
						} 
						else if (targetType == UUID.class) {
							UUID s = UUID.fromString((String)value);
							if (s != null)
								aspect.setValue(entity, mappings[i], s);
						}
						else if (targetType == BigDecimal.class && value instanceof Double) {
							BigDecimal bd = new BigDecimal((Double)value);
							aspect.setValue(entity, mappings[i], bd);
						}
						else
							aspect.setValue(entity, mappings[i], value);
					}
				}
			return entity;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public final Iterator<T> iterator() {
		return this;
	}

	public void remove() {
		// fire the dude who added remove on the iterator.

	}

}
