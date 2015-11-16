package com.eixox.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

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

	public final synchronized void addRow(Map<String, Object> map) {
		if (this.cols.size() == 0)
			addFirstRow(map);
		else {
			int imax = map.size() > this.cols.size() ? map.size() : this.cols.size();
			Object[] row = new Object[imax];
			for (Entry<String, Object> item : map.entrySet()) {
				int ordinal = getOrdinal(item.getKey());
				if (ordinal < 0) {
					ordinal = this.cols.size();
					this.cols.add(item.getKey());
					if (ordinal >= imax) {
						row = Arrays.copyOf(row, row.length + 1);
						imax = row.length;
					}
				}
				row[ordinal] = item.getValue();
			}
			this.rows.add(row);
		}
	}

	private synchronized final void addFirstRow(Map<String, Object> map) {
		Object[] row = new Object[map.size()];
		int ordinal = 0;
		for (Entry<String, Object> item : map.entrySet()) {
			this.cols.add(item.getKey());
			row[ordinal] = item.getValue();
			ordinal++;
		}
		this.rows.add(row);
	}
}
