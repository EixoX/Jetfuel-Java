package com.eixox.data;

public final class Delete extends Filtered<Delete> {

	private final Storage<?> storage;

	public Delete(Storage<?> storage) {
		super(storage.getAspect());
		this.storage = storage;
	}

	@Override
	protected final Delete getThis() {
		return this;
	}

	public final Storage<?> getStorage() {
		return this.storage;
	}

}
