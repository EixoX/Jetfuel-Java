package com.eixox.data;

import com.eixox.PairList;

public final class Update extends Filtered<Update> {

	private final Storage<?>				storage;
	private final PairList<Integer, Object>	values;

	protected Update(Storage<?> storage) {
		super(storage.getAspect());
		this.storage = storage;
		this.values = new PairList<Integer, Object>(storage.getAspect().getCount());
	}

	public final PairList<Integer, Object> getValues() {
		return this.values;
	}

	public final Update set(int ordinal, Object value) {
		this.values.add(ordinal, value);
		return this;
	}

	public final Update set(String name, Object value) {
		return set(getAspect().getOrdinalOrException(name), value);
	}

	@Override
	protected final Update getThis() {
		return this;
	}

	public final long execute() {
		return this.storage.executeUpdate(this);
	}
}
