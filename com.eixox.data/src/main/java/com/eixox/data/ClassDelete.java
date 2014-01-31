package com.eixox.data;

public abstract class ClassDelete {

	private final ClassStorage storage;
	private FilterExpression filter;

	// Description Here:
	// _____________________________________________________
	public ClassDelete(ClassStorage storage) {
		this.storage = storage;
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
	private final ClassDelete where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete where(int ordinal, Object value) {
		return where(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete where(String name, Object value) {
		return where(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete and(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.and(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete and(int ordinal, Object value) {
		return and(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete and(String name, Object value) {
		return and(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete or(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.or(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete or(int ordinal, Object value) {
		return or(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassDelete or(String name, Object value) {
		return or(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	protected abstract long execute(ClassStorage storage, FilterExpression filter);

	// Description Here:
	// _____________________________________________________
	public long execute() {
		return execute(this.storage, this.filter);
	}
}
