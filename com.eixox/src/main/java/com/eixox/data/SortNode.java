package com.eixox.data;

public class SortNode {

	public final ColumnSchema columnSchema;
	public final int ordinal;
	public final SortDirection direction;
	public SortNode next;

	public SortNode(ColumnSchema columnSchema, int ordinal, SortDirection direction) {
		this.columnSchema = columnSchema;
		this.ordinal = ordinal;
		this.direction = direction;
	}

	public SortNode(ColumnSchema columnSchema, String name, SortDirection direction) {
		this(columnSchema, columnSchema.getOrdinalOrException(name), direction);
	}

}
