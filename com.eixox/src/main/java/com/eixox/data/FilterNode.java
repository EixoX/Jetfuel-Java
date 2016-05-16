package com.eixox.data;

public class FilterNode implements Filter {

	public final Filter filter;
	public FilterOperation operation;
	public FilterNode next;

	public FilterNode(FilterTerm term) {
		this.filter = term;
	}

	public FilterNode(FilterExpression exp) {
		this.filter = exp;
	}

	public final FilterType getFilterType() {
		return FilterType.NODE;
	}

}
