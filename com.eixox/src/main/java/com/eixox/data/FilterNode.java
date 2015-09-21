package com.eixox.data;

public class FilterNode implements Filter {

	public final Filter filter;
	public FilterOperation operation;
	public FilterNode next;

	public FilterNode(Filter filter) {
		this.filter = filter;
	}

	public final FilterType getFilterType() {
		return FilterType.NODE;
	}

}
