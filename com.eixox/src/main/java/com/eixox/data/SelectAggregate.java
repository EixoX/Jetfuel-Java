package com.eixox.data;

public class SelectAggregate {

	public Aggregate aggregate;
	public String name;
	public String alias;

	public SelectAggregate(Aggregate aggregate, String name, String alias) {
		this.aggregate = aggregate;
		this.name = name;
		this.alias = alias;
	}

	@Override
	public String toString() {
		return aggregate.toString() + "(" + name + ") as " + alias;
	}

	public String getCaption() {
		return alias == null || alias.isEmpty() ? name : alias;
	}
}
