package com.eixox.data;

public class FilterTerm implements Filter {

	public String name;
	public FilterComparison comparison;
	public Object value;

	public FilterTerm(String name, FilterComparison comparison, Object value) {
		this.name = name;
		this.comparison = comparison;
		this.value = value;
	}

	public FilterTerm(String name, Object value) {
		this(name, FilterComparison.EQUAL_TO, value);
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}

}
