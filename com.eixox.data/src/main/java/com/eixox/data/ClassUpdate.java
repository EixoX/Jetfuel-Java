package com.eixox.data;

import java.util.HashMap;

public abstract class ClassUpdate {

	private final ClassStorage storage;
	private final HashMap<Integer, Object> values;
	private FilterExpression filter;

	// Description Here:
	// _____________________________________________________
	public ClassUpdate(ClassStorage storage) {
		this.storage = storage;
		this.values = new HashMap<Integer, Object>(storage.getColumnCount());
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage getStorage() {
		return this.storage;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression getFilter() {
		return this.filter;
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(int ordinal) {
		return this.values.get(ordinal);
	}

	// Description Here:
	// _____________________________________________________
	public final Object get(String name) {
		return this.values.get(this.storage.getOrdinal(name));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate set(int ordinal, Object value) {
		this.values.put(ordinal, value);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate set(String name, Object value) {
		this.values.put(this.storage.getOrdinal(name), value);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	private final ClassUpdate where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate where(int ordinal, Object value) {
		return where(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate where(String name, Object value) {
		return where(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate and(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.and(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate and(int ordinal, Object value) {
		return and(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate and(String name, Object value) {
		return and(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate or(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.or(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate or(int ordinal, Object value) {
		return or(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassUpdate or(String name, Object value) {
		return or(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	protected abstract long execute(ClassStorage storage, FilterExpression filter, HashMap<Integer, Object> values);

	// Description Here:
	// _____________________________________________________
	public long execute() {
		return execute(this.storage, this.filter, this.values);
	}
}
