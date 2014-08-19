package com.eixox.data;

public final class FilterExpression implements Filter {

	public final FilterNode first;
	private FilterNode last;

	public FilterExpression(Filter filter) {
		this.first = new FilterNode(filter);
		this.last = this.first;
	}

	public FilterExpression(ColumnSchema columnSchema, int ordinal, FilterComparison comparison, Object value) {
		this(new FilterTerm(columnSchema, ordinal, comparison, value));
	}

	public FilterExpression(ColumnSchema columnSchema, int ordinal, Object value) {
		this(new FilterTerm(columnSchema, ordinal, value));
	}

	public FilterExpression(ColumnSchema columnSchema, String name, FilterComparison comparison, Object value) {
		this(new FilterTerm(columnSchema, name, comparison, value));
	}

	public FilterExpression(ColumnSchema columnSchema, String name, Object value) {
		this(new FilterTerm(columnSchema, name, value));
	}

	public final FilterExpression and(Filter filter) {
		this.last.operation = FilterOperation.AND;
		this.last.next = new FilterNode(filter);
		this.last = this.last.next;
		return this;
	}

	public final FilterExpression and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.first.getColumnSchema(), ordinal, comparison, value));
	}

	public final FilterExpression and(int ordinal, Object value) {
		return and(new FilterTerm(this.first.getColumnSchema(), ordinal, FilterComparison.EQUAL_TO, value));
	}

	public FilterExpression and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(this.first.getColumnSchema(), name, comparison, value));
	}

	public FilterExpression and(String name, Object value) {
		return and(new FilterTerm(this.first.getColumnSchema(), name, FilterComparison.EQUAL_TO, value));
	}

	public final FilterExpression or(Filter filter) {
		this.last.operation = FilterOperation.OR;
		this.last.next = new FilterNode(filter);
		this.last = this.last.next;
		return this;
	}

	public final FilterExpression or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.first.getColumnSchema(), ordinal, comparison, value));
	}

	public final FilterExpression or(int ordinal, Object value) {
		return or(new FilterTerm(this.first.getColumnSchema(), ordinal, FilterComparison.EQUAL_TO, value));
	}

	public FilterExpression or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(this.first.getColumnSchema(), name, comparison, value));
	}

	public FilterExpression or(String name, Object value) {
		return or(new FilterTerm(this.first.getColumnSchema(), name, FilterComparison.EQUAL_TO, value));
	}

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

	public final ColumnSchema getColumnSchema() {
		return this.first.getColumnSchema();
	}

}
