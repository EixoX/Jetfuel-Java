package com.eixox.data;

public abstract class FilterWhere<T> {

	private final ClassStorage<?> storage;

	private FilterExpression filter;

	protected FilterWhere(ClassStorage<?> storage) {
		this.storage = storage;
	}

	protected abstract T getT();

	// Description Here:
	// _____________________________________________________
	public final ClassStorage<?> getStorage() {
		return this.storage;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression getFilter() {
		return this.filter;
	}

	// Description Here:
	// _____________________________________________________
	private final T where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return getT();
	}

	// Description Here:
	// _____________________________________________________
	public final T where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T where(int ordinal, Object value) {
		return where(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T where(String name, Object value) {
		return where(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T and(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.and(filter);
		return getT();
	}

	// Description Here:
	// _____________________________________________________
	public final T and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T and(int ordinal, Object value) {
		return and(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T and(String name, Object value) {
		return and(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T or(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.or(filter);
		return getT();
	}

	// Description Here:
	// _____________________________________________________
	public final T or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T or(int ordinal, Object value) {
		return or(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final T or(String name, Object value) {
		return or(new FilterTerm(storage, name, value));
	}

}
