package com.eixox.data;

import java.lang.reflect.Field;

public class ClassStorageColumn {

	private final Field field;
	private final ColumnType columnType;
	private final int length;
	private final String columnName;
	private final boolean nullable;
	private final int offset;
	private final int position;

	// Description Here:
	// _____________________________________________________
	public ClassStorageColumn(Field field, ColumnType columnType, int length, String columnName, boolean nullable, int offset, int position) {
		this.field = field;
		this.columnType = columnType;
		this.length = length;
		this.columnName = columnName == null || columnName.isEmpty() ? field.getName() : columnName;
		this.nullable = nullable;
		this.offset = offset;
		this.position = length;
	}

	// Description Here:
	// _____________________________________________________
	public ClassStorageColumn(Field field) {
		this(field, ColumnType.Normal, -1, null, true, -1, -1);
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

}
