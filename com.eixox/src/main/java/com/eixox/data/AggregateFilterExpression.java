package com.eixox.data;

public class AggregateFilterExpression implements AggregateFilter, FilterableAggregate<AggregateFilterExpression> {

	public AggregateFilterNode first;
	public AggregateFilterNode last;

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

	public final AggregateFilterExpression having(AggregateFilterExpression expression) {
		this.first = new AggregateFilterNode(expression);
		this.last = this.first;
		return this;

	}

	public final AggregateFilterExpression having(AggregateFilterTerm term) {
		this.first = new AggregateFilterNode(term);
		this.last = this.first;
		return this;
	}

	public final AggregateFilterExpression having(Aggregate aggregate, String name, FilterComparison comparison,
			Object value) {
		return having(new AggregateFilterTerm(aggregate, name, comparison, value));
	}

	public final AggregateFilterExpression having(Aggregate aggregate, String name, Object value) {
		return having(new AggregateFilterTerm(aggregate, name, FilterComparison.EQUAL_TO, value));
	}

	public final AggregateFilterExpression andHaving(AggregateFilterExpression expression) {
		if (this.first == null)
			return having(expression);
		else {
			this.last.operation = FilterOperation.AND;
			this.last.next = new AggregateFilterNode(expression);
			this.last = this.last.next;
			return this;
		}
	}

	public final AggregateFilterExpression andHaving(AggregateFilterTerm term) {
		if (this.first == null)
			return having(term);
		else {
			this.last.operation = FilterOperation.AND;
			this.last.next = new AggregateFilterNode(term);
			this.last = this.last.next;
			return this;
		}
	}

	public final AggregateFilterExpression andHaving(Aggregate aggregate, String name, FilterComparison comparison,
			Object value) {
		return andHaving(new AggregateFilterTerm(aggregate, name, comparison, value));
	}

	public final AggregateFilterExpression andHaving(Aggregate aggregate, String name, Object value) {
		return andHaving(new AggregateFilterTerm(aggregate, name, FilterComparison.EQUAL_TO, value));
	}

	public final AggregateFilterExpression orHaving(AggregateFilterExpression expression) {
		if (this.first == null)
			return having(expression);
		else {
			this.last.operation = FilterOperation.OR;
			this.last.next = new AggregateFilterNode(expression);
			this.last = this.last.next;
			return this;
		}
	}

	public final AggregateFilterExpression orHaving(AggregateFilterTerm term) {
		if (this.first == null)
			return having(term);
		else {
			this.last.operation = FilterOperation.OR;
			this.last.next = new AggregateFilterNode(term);
			this.last = this.last.next;
			return this;
		}
	}

	public final AggregateFilterExpression orHaving(Aggregate aggregate, String name, FilterComparison comparison,
			Object value) {
		return orHaving(aggregate, name, comparison, value);
	}

	public final AggregateFilterExpression orHaving(Aggregate aggregate, String name, Object value) {
		return orHaving(aggregate, name, FilterComparison.EQUAL_TO, value);
	}

	public final AggregateFilterExpression clear() {
		this.first = null;
		this.last = null;
		return this;
	}
}
