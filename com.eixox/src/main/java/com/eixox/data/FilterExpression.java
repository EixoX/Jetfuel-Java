package com.eixox.data;

public final class FilterExpression implements Filter {

	private final FilterNode first;
	private FilterNode last;

	public FilterExpression(Filter filter) {
		this.first = new FilterNode(filter);
		this.last = this.first;
	}

	public FilterExpression(DataAspect<?> aspect, int ordinal, FilterComparison comparison, Object value) {
		this(new FilterTerm(aspect, ordinal, comparison, value));
	}

	public FilterExpression(DataAspect<?> aspect, int ordinal, Object value) {
		this(new FilterTerm(aspect, ordinal, value));
	}

	public FilterExpression(DataAspect<?> aspect, String name, FilterComparison comparison, Object value) {
		this(new FilterTerm(aspect, name, comparison, value));
	}

	public FilterExpression(DataAspect<?> aspect, String name, Object value) {
		this(new FilterTerm(aspect, name, value));
	}

	public final FilterExpression and(Filter filter) {
		this.last.setOperation(FilterOperation.AND);
		this.last.setNext(new FilterNode(filter));
		this.last = this.last.getNext();
		return this;
	}

	public final FilterExpression and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.first.getAspect(), ordinal, comparison, value));
	}

	public final FilterExpression and(int ordinal, Object value) {
		return and(new FilterTerm(this.first.getAspect(), ordinal, FilterComparison.EQUAL_TO, value));
	}

	public FilterExpression and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.first.getAspect(), name, comparison, value));
	}

	public FilterExpression and(String name, Object value) {
		return and(new FilterTerm(this.first.getAspect(), name, FilterComparison.EQUAL_TO, value));
	}

	public final FilterExpression or(Filter filter) {
		this.last.setOperation(FilterOperation.OR);
		this.last.setNext(new FilterNode(filter));
		this.last = this.last.getNext();
		return this;
	}

	public final FilterExpression or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.first.getAspect(), ordinal, comparison, value));
	}

	public final FilterExpression or(int ordinal, Object value) {
		return or(new FilterTerm(this.first.getAspect(), ordinal, FilterComparison.EQUAL_TO, value));
	}

	public FilterExpression or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.first.getAspect(), name, comparison, value));
	}

	public FilterExpression or(String name, Object value) {
		return or(new FilterTerm(this.first.getAspect(), name, FilterComparison.EQUAL_TO, value));
	}

	public final FilterNode getFirst() {
		return this.first;
	}

	public final DataAspect<?> getAspect() {
		return this.first.getAspect();
	}

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

}
