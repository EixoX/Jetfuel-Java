package com.eixox.data;

import java.util.ArrayList;

public class ClassInsert {

	private final ClassStorage<?> storage;
	private final ArrayList<Integer> ordinals;
	private final ArrayList<Object> values;

	// Description Here:
	// _____________________________________________________
	public ClassInsert(ClassStorage<?> storage) {
		this.storage = storage;
		this.ordinals = new ArrayList<Integer>(storage.size());
		this.values = new ArrayList<Object>(storage.size());
	}

	// Description Here:
	// _____________________________________________________
	public final ClassInsert add(int ordinal, Object value) {
		this.ordinals.add(ordinal);
		this.values.add(value);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassInsert add(String name, Object value) {
		return add(this.storage.getOrdinalOrException(name), value);
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(int ordinal) {
		return this.values.get(ordinal);
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(String name) {
		return this.values.get(storage.getOrdinalOrException(name));
	}

	// Description Here:
	// _____________________________________________________
	public final long execute() {
		return this.storage.insert(this.ordinals, this.values);
	}

	// Description Here:
	// _____________________________________________________
	public final Object executeScopeIdentity() {
		return this.storage.insertAndScopeIdentity(ordinals, values);
	}

}
