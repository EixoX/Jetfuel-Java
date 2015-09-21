package com.eixox.data.entities;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.eixox.data.DataSelect;
import com.eixox.data.SortDirection;
import com.eixox.data.SortExpression;
import com.eixox.data.SortNode;
import com.eixox.data.Storage;

public class EntitySelect<T> extends EntityFilterBase<EntitySelect<T>>implements Iterable<T> {

	public final Storage storage;
	public SortExpression sort;

	public int pageSize;
	public int pageOrdinal;

	public EntitySelect(EntityAspect aspect, Storage storage) {
		super(aspect);
		this.storage = storage;
	}

	public final Object readMember(int ordinal) {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.pageSize = this.pageSize;
		select.pageOrdinal = this.pageOrdinal;
		select.sort = this.sort;
		select.filter = this.filter;
		return select.getFirstMember(aspect.getColumnName(ordinal));
	}

	public final T singleResult() {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.pageSize = 1;
		select.pageOrdinal = 0;
		select.sort = this.sort;
		select.filter = this.filter;
		return select.getEntity(this.aspect);
	}

	public final long count() {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.filter = this.filter;
		return select.count();
	}

	public final boolean exists() {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.filter = this.filter;
		return select.exists();
	}

	public final EntitySelectResult<T> toResult() {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.pageSize = this.pageSize;
		select.pageOrdinal = this.pageOrdinal;
		select.sort = this.sort;
		select.filter = this.filter;
		EntitySelectResult<T> result = new EntitySelectResult<T>(select.count(), this.pageSize, this.pageOrdinal);
		select.transform(this.aspect, result.items);
		return result;
	}

	public final List<T> toList() {
		DataSelect select = this.storage.select(this.aspect.tableName);
		select.pageSize = this.pageSize;
		select.pageOrdinal = this.pageOrdinal;
		select.sort = this.sort;
		select.filter = this.filter;
		List<T> list = this.pageSize > 0 ? new ArrayList<T>(this.pageSize) : new ArrayList<T>();
		select.transform(this.aspect, list);
		return list;

	}

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

	public final EntitySelect<T> orderBy(SortDirection direction, int... ordinals) {
		this.sort = new SortExpression(direction, aspect.getColumnName(ordinals[0]));
		for (int i = 1; i < ordinals.length; i++) {
			this.sort.last.next = new SortNode(aspect.getColumnName(ordinals[i]), direction);
			this.sort.last = this.sort.last.next;
		}
		return this;
	}

	public final EntitySelect<T> orderBy(SortDirection direction, String... names) {
		int[] ordinals = new int[names.length];
		for (int i = 0; i < names.length; i++)
			ordinals[i] = aspect.getOrdinalOrException(names[i]);
		return orderBy(direction, ordinals);
	}

	public final EntitySelect<T> orderBy(String... names) {
		return orderBy(SortDirection.ASCENDING, names);
	}

	public final EntitySelect<T> thenBy(SortDirection direction, int... ordinals) {
		if (this.sort == null)
			return orderBy(direction, ordinals);
		else {
			for (int i = 0; i < ordinals.length; i++) {
				this.sort.last.next = new SortNode(aspect.getColumnName(ordinals[i]), direction);
				this.sort.last = this.sort.last.next;
			}
			return this;
		}
	}

	public final EntitySelect<T> thenBy(SortDirection direction, String... names) {
		int[] ordinals = new int[names.length];
		for (int i = 0; i < names.length; i++)
			ordinals[i] = aspect.getOrdinalOrException(names[i]);
		return thenBy(direction, ordinals);
	}

	public final EntitySelect<T> thenBy(String... names) {
		return thenBy(SortDirection.ASCENDING, names);
	}

}
