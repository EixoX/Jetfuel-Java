package com.eixox.data;

public abstract class DataDelete extends Filterable<DataDelete> {

	public final String from;

	public DataDelete(String from) {
		this.from = from;
	}

	public abstract long execute();

	@Override
	protected final DataDelete getThis() {
		return this;
	}

}
