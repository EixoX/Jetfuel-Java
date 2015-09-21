package com.eixox.data;

public class SortExpression {

	public final SortNode first;
	public SortNode last;

	public SortExpression(SortDirection direction, String... names) {
		this.first = new SortNode(names[0], direction);
		this.last = this.first;
		for (int i = 1; i < names.length; i++) {
			this.last.next = new SortNode(names[i], direction);
			this.last = this.last.next;
		}
	}

	public SortExpression(String... names) {
		this(SortDirection.ASCENDING, names);
	}

	public final SortExpression thenBy(SortDirection direction, String... names) {
		for (int i = 0; i < names.length; i++) {
			this.last.next = new SortNode(names[i], direction);
			this.last = this.last.next;
		}
		return this;
	}

	public final SortExpression thenBy(String... names) {
		return thenBy(SortDirection.ASCENDING, names);
	}

}
