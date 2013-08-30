package com.eixox.data;

import java.util.Iterator;
import java.util.List;

import com.eixox.filters.ClassFilterExtension;
import com.eixox.sorters.ClassSorterDirection;
import com.eixox.sorters.ClassSorterNode;

public final class ClassSelect<T> extends ClassFilterExtension<ClassSelect<T>> implements Iterable<T> {

	private int pageSize = 1000;
	private int pageOrdinal = 0;
	private ClassSorterNode sortFirst;
	private ClassSorterNode sortLast;
	private final ClassStorage<T> dataAspect;
	private final ClassStorageEngine engine;

	public ClassSelect(ClassStorage<T> aspect, ClassStorageEngine engine) {
		super(aspect);
		this.dataAspect = aspect;
		this.engine = engine;
	}

	@Override
	protected final ClassSelect<T> getThis() {
		return this;
	}

	public final int getPageSize() {
		return this.pageSize;
	}

	public final void setPageSize(int pageSize) {
		if (pageSize < 1 || pageSize > 1000)
			throw new RuntimeException("Page size must be in range [0, 1000]");
		else
			this.pageSize = pageSize;
	}

	public final int getPageOrdinal() {
		return this.pageOrdinal;
	}

	public final void setPageOrdinal(int pageOrdinal) {
		if (pageOrdinal < 0)
			throw new RuntimeException("Page ordinal cannot be negative");
		else
			this.pageOrdinal = pageOrdinal;
	}

	public final ClassSelect<T> page(int pageSize, int pageOrdinal) {
		this.setPageSize(pageSize);
		this.setPageOrdinal(pageOrdinal);
		return this;
	}

	public final ClassSelect<T> orderBy(int ordinal, ClassSorterDirection direction) {
		if (this.sortFirst == null) {
			this.sortFirst = new ClassSorterNode(dataAspect, ordinal, direction);
			this.sortLast = this.sortFirst;
		} else {
			this.sortLast = this.sortLast.setNext(ordinal, direction);
		}
		return this;
	}

	public final ClassSelect<T> orderBy(String name, ClassSorterDirection direction) {
		return orderBy(dataAspect.getOrdinalOrException(name), direction);
	}

	public final ClassSelect<T> orderBy(ClassSorterDirection direction, String... names) {
		for (int i = 0; i < names.length; i++)
			orderBy(names[i], direction);
		return this;
	}

	public final ClassSelect<T> orderBy(String... names) {
		return orderBy(ClassSorterDirection.Ascending, names);
	}

	public final List<T> toList() {
		return this.engine.select(this.dataAspect, getWhere(), this.sortFirst, this.pageSize, this.pageOrdinal);
	}

	@Override
	public final Iterator<T> iterator() {
		return toList().iterator();
	}

	public final T singleResult() {
		return this.engine.selectOne(this.dataAspect, getWhere(), this.sortFirst);
	}
}
