package com.eixox.data;

public class AggregateFilterNode implements AggregateFilter {

	public final AggregateFilter filter;
	public FilterOperation operation;
	public AggregateFilterNode next;

	public AggregateFilterNode(AggregateFilterTerm term) {
		this.filter = term;
	}

	public AggregateFilterNode(AggregateFilterExpression expression) {
		this.filter = expression;
	}

	public FilterType getFilterType() {
		return FilterType.NODE;
	}

}
