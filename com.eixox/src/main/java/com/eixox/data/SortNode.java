package com.eixox.data;


public final class SortNode {

	private final DataAspect<?>	aspect;
	private final int			ordinal;
	private final SortDirection	direction;
	private SortNode			next;

	public SortNode(DataAspect<?> aspect, int ordinal, SortDirection direction) {
		this.aspect = aspect;
		this.ordinal = ordinal;
		this.direction = direction;
	}

	public SortNode(DataAspect<?> aspect, String name, SortDirection direction) {
		this(aspect, aspect.getOrdinalOrException(name), direction);
	}

	public final SortNode getNext() {
		return next;
	}

	public final void setNext(SortNode next) {
		this.next = next;
	}

	public final DataAspect<?> getAspect() {
		return aspect;
	}

	public final int getOrdinal() {
		return ordinal;
	}

	public final SortDirection getDirection() {
		return direction;
	}

	public final String getColumnName() {
		return this.aspect.getColumnName(this.ordinal);
	}

}
