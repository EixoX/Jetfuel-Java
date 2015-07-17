package com.eixox.data;

public interface Column {

	public String getColumnName();
	public boolean isNullable();
	public ColumnType getColumnType();
	public boolean isReadOnly();
}
