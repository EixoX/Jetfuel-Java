package com.eixox.data;


public interface Filter {

	public ColumnSchema getColumnSchema();

	public FilterType getFilterType();
}
