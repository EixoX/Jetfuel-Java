package com.eixox.data.entities;

import java.util.Iterator;
import java.util.List;

import com.eixox.data.SortDirection;

public abstract class EntitySelect<T> extends EntityFilterBase<EntitySelect<T>> implements Iterable<T> {

	private EntitySortNode sortFirst;
	private EntitySortNode sortLast;

	public int pageSize;
	public int pageOrdinal;

	public EntitySelect(EntityAspect aspect) {
		super(aspect);
	}

	public abstract Object readMember(int ordinal);

	public abstract T singleResult();

	public abstract long count();

	public abstract boolean exists();

	public abstract EntitySelectResult<T> toResult();

	public abstract List<T> toList();

	public final Iterator<T> iterator() {
		return toList().listIterator();
	}

	public final Object readMember(String memberName) {
		return readMember(aspect.getOrdinalOrException(memberName));
	}

	public final EntitySelect<T> page(int pageSize, int pageOrdinal) {
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
		return this;
	}

	@Override
	protected final EntitySelect<T> getThis() {
		return this;
	}

	public final EntitySortNode getSort() {
		return this.sortFirst;
	}

	public final EntitySelect<T> orderBy(SortDirection direction, int... ordinal) {
		this.sortFirst = new EntitySortNode(aspect, ordinal[0], direction);
		this.sortLast = this.sortFirst;
		for (int i = 1; i < ordinal.length; i++) {
			this.sortLast.next = new EntitySortNode(aspect, ordinal[i], direction);
			this.sortLast = this.sortLast.next;
		}
		return this;
	}

	public final EntitySelect<T> orderBy(SortDirection direction, String... names) {
		this.sortFirst = new EntitySortNode(aspect, names[0], direction);
		this.sortLast = this.sortFirst;
		for (int i = 1; i < names.length; i++) {
			this.sortLast.next = new EntitySortNode(aspect, names[i], direction);
			this.sortLast = this.sortLast.next;
		}
		return this;
	}

	public final EntitySelect<T> orderBy(int... ordinal) {
		return orderBy(SortDirection.ASCENDING, ordinal);
	}

	public final EntitySelect<T> orderBy(String... names) {
		return orderBy(SortDirection.ASCENDING, names);
	}

	public final EntitySelect<T> orderBy(String name, SortDirection direction) {
		return orderBy(direction, name);
	}

	public final EntitySelect<T> orderBy(int ordinal, SortDirection direction) {
		return orderBy(direction, ordinal);
	}

	public final EntitySelect<T> thenBy(SortDirection direction, int... ordinal) {
		int first = 0;
		if (this.sortFirst == null) {
			this.sortFirst = new EntitySortNode(aspect, ordinal[0], direction);
			this.sortLast = this.sortFirst;
			first = 1;
		}
		for (int i = first; i < ordinal.length; i++) {
			this.sortLast.next = new EntitySortNode(aspect, ordinal[i], direction);
			this.sortLast = this.sortLast.next;
		}
		return this;
	}

	public final EntitySelect<T> thenBy(SortDirection direction, String... names) {
		int first = 0;
		if (this.sortFirst == null) {
			this.sortFirst = new EntitySortNode(aspect, names[0], direction);
			this.sortLast = this.sortFirst;
			first = 1;
		}
		for (int i = first; i < names.length; i++) {
			this.sortLast.next = new EntitySortNode(aspect, names[i], direction);
			this.sortLast = this.sortLast.next;
		}
		return this;
	}

	public final EntitySelect<T> thenBy(int... ordinal) {
		return thenBy(SortDirection.ASCENDING, ordinal);
	}

	public final EntitySelect<T> thenBy(String... names) {
		return thenBy(SortDirection.ASCENDING, names);
	}

	public final EntitySelect<T> thenBy(String name, SortDirection direction) {
		return thenBy(direction, name);
	}

	public final EntitySelect<T> thenBy(int ordinal, SortDirection direction) {
		return thenBy(direction, ordinal);
	}

}
