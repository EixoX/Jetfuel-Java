package com.eixox.data;

/*
 * Represents a stored column.
 */
public interface Column {

	public ColumnType getColumnType();

	public String getColumnName();

	public boolean isNullable();


}
