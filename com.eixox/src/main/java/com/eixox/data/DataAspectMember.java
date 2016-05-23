package com.eixox.data;

import java.lang.reflect.Field;
import java.sql.ResultSet;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapterFactory;

public class DataAspectMember implements Column {

	public final Field field;
	public final ColumnType columnType;
	public final String columnName;
	public final boolean nullable;
	public final ValueAdapter<?> adapter;

	public DataAspectMember(Field field, ColumnType columnType, boolean nullable, String columnName,
			ValueAdapter<?> adapter) {
		this.field = field;
		this.columnType = columnType;
		this.columnName = columnName == null || columnName.isEmpty() ? field.getName() : columnName;
		this.nullable = nullable;
		this.adapter = adapter == null ? ValueAdapterFactory.getAdapter(field.getType()) : adapter;
	}

	public final String getName() {
		return this.field.getName();
	}

	public final ColumnType getColumnType() {
		return this.columnType;
	}

	public final String getColumnName() {
		return this.columnName;
	}

	public final boolean isNullable() {
		return this.nullable;
	}

	public final Object getValue(Object entity) {
		try {
			return this.field.get(entity);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void setValue(Object entity, Object value) {
		try {
			this.field.set(entity, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void setFromResultset(ResultSet source, int position, Object target) {
		this.adapter.readIntoField(source, position, field, target);
	}

	public final void setFromResultset(ResultSet source, String name, Object target) {
		this.adapter.readIntoField(source, name, field, target);
	}

	public final void setFromString(String source, Object target) {
		this.adapter.parseIntoField(source, field, target);
	}
}
