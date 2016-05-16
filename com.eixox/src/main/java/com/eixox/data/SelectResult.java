package com.eixox.data;

import java.util.ArrayList;

public class SelectResult {

	public final ArrayList<String> columns;
	public final ArrayList<Object[]> rows;

	public SelectResult(ArrayList<String> columns, int capacity) {
		this.columns = columns;
		this.rows = new ArrayList<Object[]>(capacity);
	}

	public SelectResult() {
		this(new ArrayList<String>(), 10);
	}

	public final int getOrdinal(String name) {
		int s = this.rows.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(columns.get(i)))
				return i;
		return -1;
	}

	public final Object get(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException(name + " was not found on this result.");
		else
			return rows.get(ordinal);
	}

	public final Object get(int index) {
		return this.rows.get(index);
	}

	public final int getRowCount() {
		return this.rows.size();
	}

	public final int getColCount() {
		return this.columns.size();
	}

}
