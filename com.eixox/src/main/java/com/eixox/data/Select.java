package com.eixox.data;

import java.util.Iterator;

public final class Select<E> extends Filtered<Select<E>> implements Iterable<E> {

	private final Storage<E>	storage;
	private SortExpression		orderBy;
	private int					pageSize	= 1000;
	private int					pageOrdinal;

	public Select(Storage<E> storage) {
		super(storage.getAspect());
		this.storage = storage;
	}

	public final SelectResult<E> getResult() {
		return this.storage.executeSelect(this);
	}

	public final long count() {
		return this.storage.countWhere(this.getFilter());
	}

	public final boolean exists() {
		return this.storage.existsWhere(this.getFilter());
	}

	public final E singleResult() {
		return this.storage.readWhere(this.getFilter());
	}

	public final Iterator<E> iterator() {
		return getResult().iterator();
	}

	@Override
	protected final Select<E> getThis() {
		return this;
	}

	public final Select<E> page(int pageSize, int pageOrdinal) {
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
		return this;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public final int getPageOrdinal() {
		return pageOrdinal;
	}

	public final void setPageOrdinal(int pageOrdinal) {
		this.pageOrdinal = pageOrdinal;
	}

	public final SortExpression getSort() {
		return this.orderBy;
	}

	public final Select<E> orderBy(SortDirection direction, String... names) {
		this.orderBy = new SortExpression(this.storage.getAspect(), direction, names);
		return this;
	}

	public final Select<E> orderBy(String... names) {
		this.orderBy = new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, names);
		return this;
	}

	public final Select<E> orderBy(SortDirection direction, int... ordinals) {
		this.orderBy = new SortExpression(this.storage.getAspect(), direction, ordinals);
		return this;
	}

	public final Select<E> orderBy(int... ordinals) {
		this.orderBy = new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, ordinals);
		return this;
	}

	public final Select<E> thenBy(SortDirection direction, String... names) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), direction, names) : this.orderBy.orderBy(direction, names);
		return this;
	}

	public final Select<E> thenBy(String... names) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, names) : this.orderBy.orderBy(names);
		return this;
	}

	public final Select<E> thenBy(SortDirection direction, int... ordinals) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), direction, ordinals) : this.orderBy.orderBy(direction, ordinals);
		return this;
	}

	public final Select<E> thenBy(int... ordinals) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, ordinals) : this.orderBy.orderBy(ordinals);
		return this;
	}

}
