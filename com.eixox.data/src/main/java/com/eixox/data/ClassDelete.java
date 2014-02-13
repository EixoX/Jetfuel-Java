package com.eixox.data;

public class ClassDelete extends FilterWhere<ClassDelete> {

	// Description Here:
	// _____________________________________________________
	public ClassDelete(ClassStorage<?> storage) {
		super(storage);
	}

	// Description Here:
	// _____________________________________________________
	public final long execute() {
		return this.getStorage().deleteWhere(getFilter());
	}

	@Override
	protected final ClassDelete getT() {
		return this;
	}
}
