package com.eixox.data;

public final class FilterTerm implements Filter {

	private final DataAspect<?> aspect;
	private final int ordinal;
	private final FilterComparison comparison;
	private final Object value;

	public FilterTerm(DataAspect<?> aspect, int ordinal, FilterComparison comparison, Object value) {
		this.aspect = aspect;
		this.ordinal = ordinal;
		this.comparison = comparison;
		this.value = value;
	}

	public FilterTerm(DataAspect<?> aspect, int ordinal, Object value) {
		this(aspect, ordinal, FilterComparison.EQUAL_TO, value);
	}

	public FilterTerm(DataAspect<?> aspect, String name, FilterComparison comparison, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), comparison, value);
	}

	public FilterTerm(DataAspect<?> aspect, String name, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), value);
	}

	public final DataAspect<?> getAspect() {
		return aspect;
	}

	public final int getOrdinal() {
		return ordinal;
	}

	public final FilterComparison getComparison() {
		return comparison;
	}

	public final Object getValue() {
		return value;
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}

}
