package com.eixox.data;

import com.eixox.NameValueCollection;

public abstract class DataUpdate extends Filterable<DataUpdate> {

	public final String from;
	public final NameValueCollection<Object> values = new NameValueCollection<Object>();

	public DataUpdate(String tableName) {
		this.from = tableName;
	}

	public final DataUpdate set(String name, Object value) {
		this.values.add(name, value);
		return this;
	}

	public final Object get(String name) {
		return this.values.get(name);
	}

	public final DataUpdate reset() {
		this.values.clear();
		return this;
	}

	@Override
	protected final DataUpdate getThis() {
		return this;
	}

	public abstract long execute();

}
