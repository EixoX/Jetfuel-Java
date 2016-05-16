package com.eixox.data;

public class AggregateFilterTerm implements AggregateFilter {

	public Aggregate aggregate;
	public String name;
	public FilterComparison comparison;
	public Object value;

	public AggregateFilterTerm(Aggregate aggregate, String name, FilterComparison comparison, Object value) {
		this.aggregate = aggregate;
		this.name = name;
		this.comparison = comparison;
		this.value = value;
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}

}
