package com.eixox.data;

import com.eixox.PairList;
import com.eixox.filters.ClassFilterExtension;

public final class ClassUpdate extends ClassFilterExtension<ClassUpdate> {

	private final ClassStorage<?> dataAspect;
	private final PairList<Integer, Object> values;
	private final ClassStorageEngine engine;

	public ClassUpdate(ClassStorage<?> aspect, ClassStorageEngine engine) {
		super(aspect);
		this.dataAspect = aspect;
		this.values = new PairList<Integer, Object>(aspect.getCount());
		this.engine = engine;
	}

	public final ClassStorage<?> getDataAspect() {
		return this.dataAspect;
	}

	public final PairList<Integer, Object> getValues() {
		return this.values;
	}

	public final ClassUpdate set(int ordinal, Object value) {
		this.values.add(ordinal, value);
		return this;
	}

	public final ClassUpdate set(String name, Object value) {
		this.values.add(dataAspect.getOrdinalOrException(name), value);
		return this;
	}

	@Override
	protected final ClassUpdate getThis() {
		return this;
	}

	public final long execute() {
		return this.engine.update(this.dataAspect, this.values, this.getWhere());
	}
}
