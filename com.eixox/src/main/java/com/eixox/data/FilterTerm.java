package com.eixox.data;

public final class FilterTerm implements Filter {

	public final String name;
	public final FilterComparison comparison;
	public final Object value;

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
