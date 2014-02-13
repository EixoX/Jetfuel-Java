package com.eixox.data;

import java.util.ArrayList;

public class ClassUpdate extends FilterWhere<ClassUpdate> {

	private final ArrayList<Integer> ordinals;
	private final ArrayList<Object> values;

	// Description Here:
	// _____________________________________________________
	public ClassUpdate(ClassStorage<?> storage) {
		super(storage);
		this.ordinals = new ArrayList<Integer>(storage.size());
		this.values = new ArrayList<Object>(storage.size());
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(int ordinal) {
		return this.values.get(ordinal);
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(String name) {
		return this.values.get(this.getStorage().getOrdinalOrException(name));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate set(int ordinal, Object value) {
		this.ordinals.add(ordinal);
		this.values.add(value);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate set(String name, Object value) {
		return set(getStorage().getOrdinalOrException(name), value);
	}

	// Description Here:
	// _____________________________________________________
	public final long execute() {
		return getStorage().updateWhere(ordinals, values, getFilter());

	}

	@Override
	protected final ClassUpdate getT() {
		return this;
	}
}
