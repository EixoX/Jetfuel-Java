package com.eixox.data;

public interface FilterableAggregate<T> {

	public T having(AggregateFilterExpression expression);

	public T having(AggregateFilterTerm term);

	public T having(Aggregate aggregate, String name, FilterComparison comparison, Object value);

	public T having(Aggregate aggregate, String name, Object value);

	public T andHaving(AggregateFilterExpression expression);

	public T andHaving(AggregateFilterTerm term);

	public T andHaving(Aggregate aggregate, String name, FilterComparison comparison, Object value);

	public T andHaving(Aggregate aggregate, String name, Object value);

	public T orHaving(AggregateFilterExpression expression);

	public T orHaving(AggregateFilterTerm term);

	public T orHaving(Aggregate aggregate, String name, FilterComparison comparison, Object value);

	public T orHaving(Aggregate aggregate, String name, Object value);

}
