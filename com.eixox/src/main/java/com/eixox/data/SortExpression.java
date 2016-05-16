package com.eixox.data;

public class SortExpression implements Sortable<SortExpression> {

	public SortNode first;
	public SortNode last;

	public final boolean isEmpty() {
		return this.first == null;
	}

	public final SortExpression clear() {
		this.first = null;
		this.last = null;
		return this;
	}

	public final SortExpression orderBy(Aggregate aggregate, String name, SortDirection direction) {
		this.first = new SortNode(aggregate, name, direction);
		this.last = this.first;
		return this;
	}

	public final SortExpression orderBy(String name, SortDirection direction) {
		return orderBy(Aggregate.NONE, name, direction);
	}

	public final SortExpression orderBy(String name) {
		return orderBy(Aggregate.NONE, name, SortDirection.ASC);
	}

	public final SortExpression thenOrderBy(Aggregate aggregate, String name, SortDirection direction) {
		if (this.first == null)
			return orderBy(aggregate, name, direction);
		else {
			this.last.next = new SortNode(aggregate, name, direction);
			this.last = this.last.next;
			return this;
		}
	}

	public final SortExpression thenOrderBy(String name, SortDirection direction) {
		return thenOrderBy(Aggregate.NONE, name, direction);
	}

	public final SortExpression thenOrderBy(String name) {
		return thenOrderBy(name);
	}

}
