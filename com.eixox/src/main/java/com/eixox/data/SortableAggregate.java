package com.eixox.data;

public interface SortableAggregate<T> {

	public T orderBy(Aggregate aggregate, String name, SortDirection direction);

	public T thenOrderBy(Aggregate aggregate, String name, SortDirection direction);

}
