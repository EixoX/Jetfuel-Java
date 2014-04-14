package com.eixox.data;

import com.eixox.PairList;

public final class Insert extends PairList<Integer, Object> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1007611846242349054L;
	private final Storage<?>	storage;

	public Insert(Storage<?> storage) {
		super(storage.getAspect().getCount());
		this.storage = storage;
	}

	public final Insert add(String name, Object value) {
		super.add(storage.getAspect().getOrdinalOrException(name), value);
		return this;
	}

	public final long execute() {
		return this.storage.executeInsert(this);
	}

	public final Object executeAndScopeIdentity() {
		return this.storage.executeInsertAndScopeIdentity(this);
	}
}
