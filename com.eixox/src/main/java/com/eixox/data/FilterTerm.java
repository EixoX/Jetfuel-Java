package com.eixox.data;

public class FilterTerm implements Filter {

	public final ColumnSchema columnSchema;
	public final int ordinal;
	public final FilterComparison comparison;
	public final Object value;

	public FilterTerm(ColumnSchema columnSchema, int ordinal, FilterComparison comparison, Object value) {
		this.columnSchema = columnSchema;
		this.ordinal = ordinal;
		this.comparison = comparison;
		this.value = value;
	}

	public FilterTerm(ColumnSchema columnSchema, int ordinal, Object value) {
		this(columnSchema, ordinal, FilterComparison.EQUAL_TO, value);
	}

	public FilterTerm(ColumnSchema columnSchema, String name, FilterComparison comparison, Object value) {
		this(columnSchema, columnSchema.getOrdinalOrException(name), comparison, value);
	}

	public FilterTerm(ColumnSchema columnSchema, String name, Object value) {
		this(columnSchema, columnSchema.getOrdinalOrException(name), value);
	}

	public final ColumnSchema getColumnSchema() {
		return columnSchema;
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}

}
