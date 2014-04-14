package com.eixox.data;

import java.util.Iterator;

public final class SelectMember extends Filtered<SelectMember> implements Iterable<Object> {

	private final Storage<?>	storage;
	private final int			ordinal;
	private SortExpression		orderBy;
	private int					pageSize	= 1000;
	private int					pageOrdinal;

	public SelectMember(Storage<?> storage, int ordinal) {
		super(storage.getAspect());
		this.storage = storage;
		this.ordinal = ordinal;
	}

	public final int getOrdinal() {
		return this.ordinal;
	}

	public final SelectMemberResult getResult() {
		return this.storage.executeSelectMember(this);
	}

	public final long count() {
		return this.storage.countWhere(this.getFilter());
	}

	public final boolean exists() {
		return this.storage.existsWhere(this.getFilter());
	}

	public final Object singleResult() {
		return this.storage.readWhere(this.getFilter());
	}

	public final Iterator<Object> iterator() {
		return getResult().iterator();
	}

	@Override
	protected final SelectMember getThis() {
		return this;
	}

	public final SelectMember page(int pageSize, int pageOrdinal) {
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

	public final SelectMember orderBy(SortDirection direction, String... names) {
		this.orderBy = new SortExpression(this.storage.getAspect(), direction, names);
		return this;
	}

	public final SelectMember orderBy(String... names) {
		this.orderBy = new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, names);
		return this;
	}

	public final SelectMember orderBy(SortDirection direction, int... ordinals) {
		this.orderBy = new SortExpression(this.storage.getAspect(), direction, ordinals);
		return this;
	}

	public final SelectMember orderBy(int... ordinals) {
		this.orderBy = new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, ordinals);
		return this;
	}

	public final SelectMember thenBy(SortDirection direction, String... names) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), direction, names) : this.orderBy.orderBy(direction, names);
		return this;
	}

	public final SelectMember thenBy(String... names) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, names) : this.orderBy.orderBy(names);
		return this;
	}

	public final SelectMember thenBy(SortDirection direction, int... ordinals) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), direction, ordinals) : this.orderBy.orderBy(direction, ordinals);
		return this;
	}

	public final SelectMember thenBy(int... ordinals) {
		this.orderBy = this.orderBy == null ? new SortExpression(this.storage.getAspect(), SortDirection.ASCENDING, ordinals) : this.orderBy.orderBy(ordinals);
		return this;
	}

}
