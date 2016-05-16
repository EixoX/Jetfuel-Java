package com.eixox.data;

public interface AggregateColumn extends Column {

	public Aggregate getAggregate();

	public String getAlias();
}
