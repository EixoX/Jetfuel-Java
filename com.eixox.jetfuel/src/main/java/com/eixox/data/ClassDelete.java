package com.eixox.data;

import com.eixox.filters.ClassFilterExtension;

public final class ClassDelete extends ClassFilterExtension<ClassDelete> {

	private final ClassStorageEngine engine;

	public ClassDelete(ClassStorage<?> aspect, ClassStorageEngine engine) {
		super(aspect);
		this.engine = engine;
	}

	@Override
	protected final ClassDelete getThis() {
		return this;
	}

	public final long execute() {
		return this.engine.delete((ClassStorage<?>) getAspect(), getWhere());
	}

}
