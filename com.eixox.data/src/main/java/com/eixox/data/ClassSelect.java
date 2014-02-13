package com.eixox.data;

import java.util.Iterator;
import java.util.List;

public class ClassSelect<T> extends FilterWhere<ClassSelect<T>> implements Iterable<T> {

	private SortExpression sort;
	private int pageSize;
	private int pageOrdinal;

	// Description Here:
	// _____________________________________________________
	public ClassSelect(ClassStorage<T> storage) {
		super(storage);
	}

	// Description Here:
	// _____________________________________________________
	public final SortExpression getSort() {
		return this.sort;
	}

	// Description Here:
	// _____________________________________________________
	public final int getPageSize() {
		return this.pageSize;
	}

	// Description Here:
	// _____________________________________________________
	public final int getPageOrdinal() {
		return this.pageOrdinal;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(int ordinal, SortDirection direction) {
		this.sort = new SortExpression(this.getStorage(), ordinal, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(int ordinal) {
		this.sort = new SortExpression(this.getStorage(), ordinal);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(String name, SortDirection direction) {
		this.sort = new SortExpression(this.getStorage(), name, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(String name) {
		this.sort = new SortExpression(this.getStorage(), name);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(SortDirection direction, String... names) {
		this.sort = new SortExpression(this.getStorage(), names[0], direction);
		for (int i = 1; i < names.length; i++)
			this.sort.thenBy(names[i], direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(String... names) {
		return orderBy(SortDirection.Ascending, names);
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(SortDirection direction, int... ordinals) {
		this.sort = new SortExpression(this.getStorage(), ordinals[0], direction);
		for (int i = 1; i < ordinals.length; i++)
			this.sort.thenBy(ordinals[i], direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(int... ordinals) {
		return orderBy(SortDirection.Ascending, ordinals);
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> thenBy(int ordinal, SortDirection direction) {
		this.sort.thenBy(ordinal, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> thenBy(int ordinal) {
		this.sort.thenBy(ordinal);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> thenBy(String name, SortDirection direction) {
		this.sort.thenBy(name, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> thenBy(String name) {
		this.sort.thenBy(name);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> page(int pageSize, int pageOrdinal) {
		this.pageOrdinal = pageOrdinal;
		this.pageSize = pageSize;
		return this;
	}

	// Description Here:
	// _____________________________________________________
	@SuppressWarnings("unchecked")
	public final List<T> toList() {
		return ((ClassStorage<T>) getStorage()).select(this.getFilter(), this.sort, this.pageSize, this.pageOrdinal);
	}

	// Description Here:
	// _____________________________________________________
	public final Iterator<T> iterator() {
		return toList().iterator();
	}

	// Returns a single result query
	// _____________________________________________________
	@SuppressWarnings("unchecked")
	public final T singleResult() {
		return ((ClassStorage<T>) getStorage()).read(getFilter());
	}

	// Returns a count on the items
	// _____________________________________________________
	public final long count() {
		return getStorage().selectCount(this.getFilter());
	}

	// Returns select exists command
	// _____________________________________________________
	public final boolean exists() {
		return getStorage().selectExists(this.getFilter());
	}

	// Returns the value of a given column member.
	// _____________________________________________________
	public final Object getMemberValue(int ordinal) {
		return getStorage().readMember(ordinal, this.getFilter());
	}

	// Returns the list of values of a given column member.
	// _____________________________________________________
	public final List<Object> onlyMember(int ordinal) {
		return getStorage().selectMember(ordinal, getFilter(), sort, pageSize, pageOrdinal);
	}

	// Returns the value of a given column member.
	// _____________________________________________________
	public final Object getMemberValue(String name) {
		return getMemberValue(this.getStorage().getOrdinalOrException(name));
	}

	// Returns the list of values of a given column member.
	// _____________________________________________________
	public final List<Object> onlyMember(String name) {
		return onlyMember(this.getStorage().getOrdinalOrException(name));
	}

	@Override
	protected final ClassSelect<T> getT() {
		return this;
	}
}
