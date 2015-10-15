package com.eixox.data;

import java.util.ArrayList;

public class DataSelectResult extends ArrayList<Object[]> {

	private static final long serialVersionUID = -9201186867408890430L;

	public final ArrayList<String> cols;

	public int pageSize;
	public int pageOrdinal;
	public int totalCount = -1;

	public DataSelectResult() {
		this.cols = new ArrayList<String>();
	}

	public DataSelectResult(int capacity) {
		super(capacity);
		this.cols = new ArrayList<String>();
	}

	public final int getOrdinal(String name) {
		int s = cols.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(cols.get(i)))
				return i;
		return -1;
	}

	public final Object get(int row, int col) {
		if (row < 0 || row >= size())
			return null;
		else {
			Object[] items = get(row);
			return col < 0 || items == null || col >= items.length ? null : items[col];
		}
	}
}
