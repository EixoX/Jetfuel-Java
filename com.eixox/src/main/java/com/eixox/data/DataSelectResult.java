package com.eixox.data;

import java.util.ArrayList;

public class DataSelectResult {

	public final ArrayList<String> cols;
	public final ArrayList<Object[]> rows;

	public int pageSize;
	public int pageOrdinal;
	public int totalCount = -1;

	public DataSelectResult() {
		this.cols = new ArrayList<String>();
		this.rows = new ArrayList<Object[]>();
	}

	public DataSelectResult(int capacity) {
		this.rows = new ArrayList<Object[]>(capacity);
		this.cols = new ArrayList<String>();
	}

	public final int getOrdinal(String name) {
		int s = cols.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(cols.get(i)))
				return i;
		return -1;
	}

	public final String getColumn(int ordinal) {
		return this.cols.get(ordinal);
	}

	public final Object[] getRow(int ordinal) {
		return this.rows.get(ordinal);
	}

	public final Object get(int row, int col) {
		if (row < 0 || row >= this.rows.size())
			return null;
		else {
			Object[] items = this.rows.get(row);
			return col < 0 || items == null || col >= items.length ? null : items[col];
		}
	}
}
