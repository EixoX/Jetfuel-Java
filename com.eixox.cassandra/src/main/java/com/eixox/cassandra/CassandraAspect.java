package com.eixox.cassandra;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.eixox.data.entities.DataAspect;
import com.eixox.data.entities.DataAspectField;
import com.eixox.data.entities.DataAspectMember;

public class CassandraAspect<T> extends DataAspect<T, DataAspectMember> {

	@Override
	protected DataAspectMember decorate(Field field) {
		CassandraColumn column = field.getAnnotation(CassandraColumn.class);
		if (column == null)
			return null;
		else
			return new DataAspectField(field, column.columnType(), column.name(), false);
	}

	private CassandraAspect(Class<T> dataType, String tableName) {
		super(dataType, tableName);
	}

	private static final HashMap<Class<?>, CassandraAspect<?>> INSTANCES = new HashMap<Class<?>, CassandraAspect<?>>();

	private static final String getTableName(Class<?> claz) {
		CassandraTable tbl = claz.getAnnotation(CassandraTable.class);
		return tbl == null ? null : tbl.name();
	}

	@SuppressWarnings("unchecked")
	public static synchronized final <T> CassandraAspect<T> getInstance(Class<T> claz) {
		CassandraAspect<T> aspect = (CassandraAspect<T>) INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new CassandraAspect<T>(claz, getTableName(claz));
			INSTANCES.put(claz, aspect);
		}
		return aspect;
	}

}
