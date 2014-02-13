package com.eixox.data;

// Description Here:
// _____________________________________________________
public class FilterNode implements Filter {

	private final Filter filter;
	private FilterOperation operation;
	private FilterNode next;

	// Description Here:
	// _____________________________________________________
	public FilterNode(Filter filter) {
		this.filter = filter;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterType getFilterType() {
		return FilterType.Node;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterOperation getOperation() {
		return operation;
	}

	// Description Here:
	// _____________________________________________________
	public final void setOperation(FilterOperation operation) {
		this.operation = operation;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterNode getNext() {
		return next;
	}

	// Description Here:
	// _____________________________________________________
	public final void setNext(FilterNode next) {
		this.next = next;
	}

	// Description Here:
	// _____________________________________________________
	public final Filter getFilter() {
		return filter;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage<?> getStorage() {
		return filter.getStorage();
	}

}
