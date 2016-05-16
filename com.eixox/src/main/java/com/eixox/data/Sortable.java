package com.eixox.data;

public interface Sortable<T> {


	public T orderBy(String name, SortDirection direction);

	public T orderBy(String name);

	public T thenOrderBy(String name, SortDirection direction);

	public T thenOrderBy(String name);

}
