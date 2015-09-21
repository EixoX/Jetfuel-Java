package com.eixox.data;

public class SortNode {

	public String name;
	public SortDirection direction;
	public SortNode next;

	public SortNode(String name, SortDirection direction) {
		this.name = name;
		this.direction = direction;
	}

	public SortNode(String name) {
		this(name, SortDirection.ASCENDING);
	}

}
