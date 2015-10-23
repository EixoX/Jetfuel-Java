package com.eixox.data;

import java.util.Iterator;
import java.util.List;

import com.eixox.data.entities.EntityAspect;

public abstract class DataSelect
		extends Filterable<DataSelect>
		implements Sortable<DataSelect>,
		Iterable<Object[]> {

	public final String from;
	public SortExpression sort;
	public int pageSize;
	public int pageOrdinal;

	public DataSelect(String from) {
		this.from = from;
	}

	public abstract DataSelectResult toResult();

	public abstract long count();

	public abstract boolean exists();

	public abstract Object getFirstMember(String name);

	public abstract List<Object> getMember(String name);

	public abstract DataSelectResult getMembers(String... names);

	public abstract Object[] getFirstMembers(String... names);

	public abstract <T> long transform(EntityAspect aspect, List<T> list);

	public abstract <T> T getEntity(EntityAspect aspect);

	@Override
	protected final DataSelect getThis() {
		return this;
	}

	public final DataSelect page(int pageSize, int pageOrdinal) {
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
		return this;
	}

	public final Iterator<Object[]> iterator() {
		return toResult().rows.iterator();
	}

	public final DataSelect orderBy(SortDirection direction, String... names) {
		this.sort = new SortExpression(direction, names);
		return this;
	}

	public final DataSelect orderBy(String... names) {
		this.sort = new SortExpression(names);
		return this;
	}

	public final DataSelect thenBy(SortDirection direction, String... names) {
		this.sort = this.sort == null ? new SortExpression(direction, names) : this.sort.thenBy(direction, names);
		return this;
	}

	public final DataSelect thenBy(String... names) {
		this.sort = this.sort == null ? new SortExpression(names) : this.sort.thenBy(names);
		return this;
	}

}
