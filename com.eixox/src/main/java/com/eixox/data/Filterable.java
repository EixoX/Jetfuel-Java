package com.eixox.data;

public interface Filterable<T> {

	public T where(FilterTerm term);

	public T where(FilterExpression expression);

	public T where(String name, FilterComparison comparison, Object value);

	public T where(String name, Object value);

	public T andWhere(FilterTerm term);

	public T andWhere(FilterExpression expression);

	public T andWhere(String name, FilterComparison comparison, Object value);

	public T andWhere(String name, Object value);

	public T orWhere(FilterTerm term);

	public T orWhere(FilterExpression expression);

	public T orWhere(String name, FilterComparison comparison, Object value);

	public T orWhere(String name, Object value);
}
