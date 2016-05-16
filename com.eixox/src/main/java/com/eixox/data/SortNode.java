package com.eixox.data;

public final class SortNode {

	public final Aggregate aggregate;
	public final String name;
	public final SortDirection direction;
	public SortNode next;

	public SortNode(Aggregate aggregate, String name, SortDirection direction) {
		this.aggregate = aggregate;
		this.name = name;
		this.direction = direction;
	}
}
