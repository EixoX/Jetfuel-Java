package com.eixox.data;

public abstract class Filterable<T> {

	public FilterExpression filter;

	protected abstract T getThis();

	public final T where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return getThis();
	}

	public final T where(String name, Object value) {
		return where(new FilterTerm(name, value));
	}

	public final T where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(name, comparison, value));
	}

	public final T and(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.and(filter);
		return getThis();
	}

	public final T and(String name, Object value) {
		return and(new FilterTerm(name, value));
	}

	public final T and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(name, comparison, value));
	}

	public final T or(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.or(filter);
		return getThis();
	}

	public final T or(String name, Object value) {
		return or(new FilterTerm(name, value));
	}

	public final T or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(name, comparison, value));
	}
}
