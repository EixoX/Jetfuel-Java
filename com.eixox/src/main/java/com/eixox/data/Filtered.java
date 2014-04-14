package com.eixox.data;

public abstract class Filtered<T> {

	private final DataAspect	aspect;
	private FilterExpression	filter;

	protected Filtered(DataAspect aspect) {
		this.aspect = aspect;
	}

	protected abstract T getThis();

	public final DataAspect getAspect() {
		return this.aspect;
	}

	public final FilterExpression getFilter() {
		return this.filter;
	}

	public final T where(Filter where) {
		this.filter = new FilterExpression(where);
		return getThis();
	}

	public final T where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(aspect, ordinal, comparison, value));
	}

	public final T where(int ordinal, Object value) {
		return where(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public final T where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(aspect, name, comparison, value));
	}

	public final T where(String name, Object value) {
		return where(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));
	}

	public final T and(Filter where) {
		this.filter = this.filter == null ? new FilterExpression(where) : this.filter.and(where);
		return getThis();
	}

	public final T and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.aspect, ordinal, comparison, value));
	}

	public final T and(int ordinal, Object value) {
		return and(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public final T and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.aspect, name, comparison, value));
	}

	public final T and(String name, Object value) {
		return and(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));
	}

	public final T or(Filter where) {
		this.filter = this.filter == null ? new FilterExpression(where) : this.filter.or(where);
		return getThis();
	}

	public final T or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.aspect, ordinal, comparison, value));
	}

	public final T or(int ordinal, Object value) {
		return or(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public final T or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.aspect, name, comparison, value));
	}

	public final T or(String name, Object value) {
		return or(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));

	}
}
