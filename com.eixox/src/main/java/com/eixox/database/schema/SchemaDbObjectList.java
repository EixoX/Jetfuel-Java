package com.eixox.database.schema;

import java.util.ArrayList;

public class SchemaDbObjectList<T extends SchemaDbObject> extends ArrayList<T> {

	private static final long serialVersionUID = -9024270079114052033L;

	public SchemaDbObjectList() {
	}

	public SchemaDbObjectList(int capacity) {
		super(capacity);
	}

	public int indexOf(String name) {
		int s = super.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(super.get(i).name))
				return i;
		return -1;
	}

	public T get(String name) {
		int ordinal = indexOf(name);
		return ordinal >= 0 ? super.get(ordinal) : null;
	}

	public boolean contains(String name) {
		return indexOf(name) >= 0;
	}
}
