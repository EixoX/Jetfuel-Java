package com.eixox.data;

public class FilterExpression implements Filter, Filterable<FilterExpression> {

	public FilterNode first;
	public FilterNode last;

	public final boolean isEmpty() {
		return this.first == null;
	}

	public final FilterExpression clear() {
		this.first = null;
		this.last = null;
		return this;
	}

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

	public final FilterExpression where(FilterTerm term) {
		this.first = new FilterNode(term);
		this.last = this.first;
		return this;
	}

	public final FilterExpression where(FilterExpression expression) {
		this.first = new FilterNode(expression);
		this.last = this.first;
		return this;
	}

	public final FilterExpression where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(name, comparison, value));
	}

	public final FilterExpression where(String name, Object value) {
		return where(new FilterTerm(name, value));
	}

	public final FilterExpression andWhere(FilterTerm term) {
		if (this.first == null) {
			this.first = new FilterNode(term);
			this.last = this.first;
		} else {
			this.last.next = new FilterNode(term);
			this.last.operation = FilterOperation.AND;
			this.last = this.last.next;
		}
		return this;
	}

	public final FilterExpression andWhere(FilterExpression expression) {
		if (this.first == null) {
			this.first = new FilterNode(expression);
			this.last = this.first;
		} else {
			this.last.next = new FilterNode(expression);
			this.last.operation = FilterOperation.AND;
			this.last = this.last.next;
		}
		return this;
	}

	public final FilterExpression andWhere(String name, FilterComparison comparison, Object value) {
		return andWhere(new FilterTerm(name, comparison, value));
	}

	public final FilterExpression andWhere(String name, Object value) {
		return andWhere(new FilterTerm(name, value));
	}

	public final FilterExpression orWhere(FilterTerm term) {
		if (this.first == null) {
			this.first = new FilterNode(term);
			this.last = this.first;
		} else {
			this.last.next = new FilterNode(term);
			this.last.operation = FilterOperation.OR;
			this.last = this.last.next;
		}
		return this;
	}

	public final FilterExpression orWhere(FilterExpression expression) {
		if (this.first == null) {
			this.first = new FilterNode(expression);
			this.last = this.first;
		} else {
			this.last.next = new FilterNode(expression);
			this.last.operation = FilterOperation.OR;
			this.last = this.last.next;
		}
		return this;
	}

	public final FilterExpression orWhere(String name, FilterComparison comparison, Object value) {
		return orWhere(new FilterTerm(name, comparison, value));
	}

	public final FilterExpression orWhere(String name, Object value) {
		return orWhere(new FilterTerm(name, value));
	}

}
