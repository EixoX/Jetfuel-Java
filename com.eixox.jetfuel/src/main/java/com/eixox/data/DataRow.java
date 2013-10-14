package com.eixox.data;

public class DataRow {

	private final DataTable table;
	private final Object[] data;

	DataRow(DataTable table, int size) {
		this.table = table;
		this.data = new Object[size];
	}

	/**
	 * @return the table
	 */
	public final DataTable getTable() {
		return table;
	}

	/**
	 * @return the data
	 */
	public final Object[] getData() {
		return data;
	}

	public final Object getValue(int columnOrdinal) {
		return columnOrdinal >= 0 && columnOrdinal < data.length ? data[columnOrdinal] : null;
	}

	public final void setValue(int columnOrdinal, Object value) {
		this.data[columnOrdinal] = value;
	}
}
