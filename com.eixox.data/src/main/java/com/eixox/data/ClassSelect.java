package com.eixox.data;

import java.util.Iterator;
import java.util.List;

public abstract class ClassSelect<T> implements Iterable<T> {

	private final ClassStorage storage;
	private FilterExpression filter;
	private SortExpression sort;
	private int pageSize;
	private int pageOrdinal;

	// Description Here:
	// _____________________________________________________
	public ClassSelect(ClassStorage storage) {
		this.storage = storage;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage getStorage() {
		return this.storage;
	}

	// Description Here:
	// _____________________________________________________
	public final FilterExpression getFilter() {
		return this.filter;
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
	private final ClassSelect<T> where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> where(int ordinal, Object value) {
		return where(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> where(String name, Object value) {
		return where(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> and(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.and(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> and(int ordinal, Object value) {
		return and(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> and(String name, Object value) {
		return and(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> or(Filter filter) {
		if (this.filter == null)
			this.filter = new FilterExpression(filter);
		else
			this.filter.or(filter);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, ordinal, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> or(int ordinal, Object value) {
		return or(new FilterTerm(storage, ordinal, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(storage, name, comparison, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> or(String name, Object value) {
		return or(new FilterTerm(storage, name, value));
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(int ordinal, SortDirection direction) {
		this.sort = new SortExpression(storage, ordinal, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(int ordinal) {
		this.sort = new SortExpression(storage, ordinal);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(String name, SortDirection direction) {
		this.sort = new SortExpression(storage, name, direction);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(String name) {
		this.sort = new SortExpression(storage, name);
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassSelect<T> orderBy(SortDirection direction, String... names) {
		this.sort = new SortExpression(storage, names[0], direction);
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
		this.sort = new SortExpression(storage, ordinals[0], direction);
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
	protected abstract List<T> toList(ClassStorage storage, FilterExpression filter, SortExpression sort, int pageSize, int pageOrdinal);

	// Description Here:
	// _____________________________________________________
	public final List<T> toList() {
		return toList(this.storage, this.filter, this.sort, this.pageSize, this.pageOrdinal);
	}

	// Description Here:
	// _____________________________________________________
	public final Iterator<T> iterator() {
		return toList().iterator();
	}

}
