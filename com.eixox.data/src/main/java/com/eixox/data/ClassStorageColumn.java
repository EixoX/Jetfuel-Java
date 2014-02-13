package com.eixox.data;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Locale;

public class ClassStorageColumn implements ValueAdapter<Object> {

	private final Field field;
	private final ColumnType columnType;
	private final int length;
	private final String columnName;
	private final boolean nullable;
	private final int offset;
	private final int position;
	private final ValueAdapter<?> adapter;

	// Description Here:
	// _____________________________________________________
	public ClassStorageColumn(Field field, Column column) {
		this.field = field;
		this.field.setAccessible(true);
		this.columnType = column.type();
		this.length = column.length();
		this.columnName = column.name() == null || column.name().isEmpty() ? field.getName() : column.name();
		this.nullable = column.nullable();
		this.offset = column.offset();
		this.position = column.length();
		this.adapter = ValueAdapters.getAdapter(field.getType());
	}

	// Description Here:
	// _____________________________________________________
	public final Field getField() {
		return field;
	}

	// Description Here:
	// _____________________________________________________
	public final ColumnType getColumnType() {
		return columnType;
	}

	// Description Here:
	// _____________________________________________________
	public final int getLength() {
		return length;
	}

	// Description Here:
	// _____________________________________________________
	public final String getColumnName() {
		return columnName;
	}

	// Description Here:
	// _____________________________________________________
	public final boolean isNullable() {
		return nullable;
	}

	// Description Here:
	// _____________________________________________________
	public final int getOffset() {
		return offset;
	}

	// Description Here:
	// _____________________________________________________
	public final int getPosition() {
		return position;
	}

	// Description Here:
	// _____________________________________________________
	public final String getName() {
		return this.field.getName();
	}

	// Description Here:
	// _____________________________________________________
	public final ValueAdapter<?> getAdapter() {
		return this.adapter;
	}

	// Description Here:
	// _____________________________________________________
	public final Object getValue(Object entity) {
		try {
			return this.field.get(entity);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	// Description Here:
	// _____________________________________________________
	public final void setValue(Object entity, Object value) {
		try {
			if (value != null && !this.field.getType().isInstance(value))
				value = this.adapter.convert(value);
			this.field.set(entity, value);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public final Object convert(Object value) {
		return this.adapter.convert(value);
	}

	public final Object parse(String text) {
		return this.adapter.parse(text);
	}

	public final Object parse(String text, Locale locale) {
		return this.adapter.parse(text, locale);
	}

	public final String format(Object value) {
		return this.adapter.format(value);
	}

	public final String format(Object value, Locale locale) {
		return this.adapter.format(value, locale);
	}

	public final String formatSql(Object object) {
		return this.adapter.formatSql(object);
	}

	public final Object readFrom(ResultSet rs, int ordinal) {
		return this.adapter.readFrom(rs, ordinal);
	}
}
