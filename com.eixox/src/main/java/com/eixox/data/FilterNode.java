package com.eixox.data;


public final class FilterNode implements Filter {

	private final Filter	filter;
	private FilterOperation	operation;
	private FilterNode		next;

	public FilterNode(Filter filter) {
		this.filter = filter;
	}

	public final FilterOperation getOperation() {
		return operation;
	}

	public final void setOperation(FilterOperation operation) {
		this.operation = operation;
	}

	public final FilterNode getNext() {
		return next;
	}

	public final void setNext(FilterNode next) {
		this.next = next;
	}

	public final Filter getFilter() {
		return filter;
	}

	public final DataAspect<?> getAspect() {
		return this.filter.getAspect();
	}

	public FilterType getFilterType() {
		return FilterType.NODE;
	}

}
