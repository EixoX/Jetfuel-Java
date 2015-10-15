package com.eixox.data;

public final class FilterExpression implements Filter {

	public final FilterNode first;
	private FilterNode last;

	public FilterExpression(Filter filter) {
		this.first = new FilterNode(filter);
		this.last = this.first;
	}

	public FilterExpression(String name, FilterComparison comparison, Object value) {
		this(new FilterTerm(name, comparison, value));
	}

	public FilterExpression(String name, Object value) {
		this(new FilterTerm(name, value));
	}

	public final FilterExpression and(Filter filter) {
		this.last.operation = FilterOperation.AND;
		this.last.next = new FilterNode(filter);
		this.last = this.last.next;
		return this;
	}

	public FilterExpression and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(name, comparison, value));
	}

	public FilterExpression and(String name, Object value) {
		return and(new FilterTerm(name, value));
	}

	public final FilterExpression or(Filter filter) {
		this.last.operation = FilterOperation.OR;
		this.last.next = new FilterNode(filter);
		this.last = this.last.next;
		return this;
	}

	public FilterExpression or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(name, comparison, value));
	}

	public FilterExpression or(String name, Object value) {
		return or(new FilterTerm(name, value));
	}

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

}
