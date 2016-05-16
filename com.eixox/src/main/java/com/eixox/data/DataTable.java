package com.eixox.data;

import java.util.ArrayList;

public class DataTable {

	public final ColumnSchema<?> columns;
	public final ArrayList<Object[]> rows;

	public DataTable(ColumnSchema<?> columns) {
		this.columns = columns;
		this.rows = new ArrayList<Object[]>();
	}
}
