package com.eixox.data;

// Description Here:
// _____________________________________________________
public class FilterExpression implements Filter {

	private final FilterNode first;
	private FilterNode last;

	// Description Here:
	// _____________________________________________________
	public FilterExpression(Filter filter) {
		this.first = new FilterNode(filter);
	}

	// Description Here:
	// _____________________________________________________
	public FilterExpression(ClassStorage storage, int ordinal, FilterComparison comparison, Object value) {
		this(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public FilterExpression(ClassStorage storage, int ordinal, Object value) {
		this(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public FilterExpression(ClassStorage storage, String name, FilterComparison comparison, Object value) {
		this(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public FilterExpression(ClassStorage storage, String name, Object value) {
		this(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterNode getFirst() {
		return this.first;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterNode getLast() {
		return this.last;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage getStorage() {
		return this.first.getStorage();
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression and(Filter filter) {
		FilterNode node = new FilterNode(filter);
		this.last.setOperation(FilterOperation.And);
		this.last.setNext(node);
		this.last = node;
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(first.getStorage(), ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression and(int ordinal, Object value) {
		return and(new FilterTerm(first.getStorage(), ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(first.getStorage(), name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression and(String name, Object value) {
		return and(new FilterTerm(first.getStorage(), name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression or(Filter filter) {
		FilterNode node = new FilterNode(filter);
		this.last.setOperation(FilterOperation.Or);
		this.last.setNext(node);
		this.last = node;
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression or(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(first.getStorage(), ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression or(int ordinal, Object value) {
		return and(new FilterTerm(first.getStorage(), ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression or(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(first.getStorage(), name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression or(String name, Object value) {
		return and(new FilterTerm(first.getStorage(), name, value));
	}
}
