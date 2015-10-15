package com.eixox.data;

public interface Sortable<T> {

	public T orderBy(SortDirection direction, String... names);

	public T orderBy(String... names);

	public T thenBy(SortDirection direction, String... names);

	public T thenBy(String... names);
}
