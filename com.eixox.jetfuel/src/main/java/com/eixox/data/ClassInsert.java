package com.eixox.data;

import com.eixox.PairList;

public final class ClassInsert extends PairList<Integer, Object> {

	private static final long serialVersionUID = 1309018319180363348L;
	private final ClassStorage<?> aspect;
	private final ClassStorageEngine engine;

	public ClassInsert(ClassStorage<?> aspect, ClassStorageEngine engine) {
		super(aspect.getCount());
		this.aspect = aspect;
		this.engine = engine;
	}

	public final ClassInsert add(String name, Object value) {
		super.add(aspect.getOrdinalOrException(name), value);
		return this;
	}

	public final ClassStorage<?> getDataAspect() {
		return this.aspect;
	}

	public final long execute() {
		return this.engine.insert(this.aspect, this);
	}

	public final Object executeAndScopeIdentity() {
		return this.engine.insertAndScopeIdentity(this.aspect, this, this.aspect.getIdentityOrdinal());
	}

}
