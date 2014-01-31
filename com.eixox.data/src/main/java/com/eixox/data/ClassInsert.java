package com.eixox.data;

import java.util.HashMap;

public abstract class ClassInsert {

	private final ClassStorage storage;
	private final HashMap<Integer, Object> values;

	public ClassInsert(ClassStorage storage) {
		this.storage = storage;
		this.values = new HashMap<Integer, Object>(storage.getColumnCount());
	}

	public final ClassInsert add(int ordinal, Object value) {
		this.values.put(ordinal, value);
		return this;
	}

	public final ClassInsert add(String name, Object value) {
		this.values.put(storage.getOrdinal(name), value);
		return this;
	}

	protected abstract int execute(ClassStorage storage, HashMap<Integer, Object> values);

	protected abstract Object executeScopeIdentity(ClassStorage storage, HashMap<Integer, Object> values);

	public final int execute() {
		return execute(this.storage, this.values);
	}

	public final Object executeScopeIdentity() {
		return executeScopeIdentity(this.storage, this.values);
	}

}
