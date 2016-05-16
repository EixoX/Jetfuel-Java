package com.eixox.data.text;

import java.lang.reflect.Field;

import com.eixox.data.ColumnType;
import com.eixox.data.adapters.ValueAdapter;
import com.eixox.data.adapters.ValueAdapterFactory;

public class TextAspectMember implements TextColumn {

	public final Field field;
	public final ColumnType columnType;
	public final String columnName;
	public final boolean nullable;
	public final ValueAdapter<?> adapter;

	public TextAspectMember(Field field, ColumnType columnType, String columnName, boolean isNullable,
			ValueAdapter<?> adapter) {
		this.field = field;
		this.columnType = columnType;
		this.columnName = columnName == null || columnName.isEmpty() ? field.getName() : columnName;
		this.nullable = isNullable;
		this.adapter = adapter == null ? ValueAdapterFactory.getAdapter(field.getClass()) : adapter;
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

	public final ValueAdapter<?> getAdapter() {
		return this.adapter;
	}

}
