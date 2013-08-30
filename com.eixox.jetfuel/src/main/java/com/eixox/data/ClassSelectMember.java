package com.eixox.data;

import java.util.Iterator;
import java.util.List;

import com.eixox.filters.ClassFilterExtension;
import com.eixox.sorters.ClassSorterDirection;
import com.eixox.sorters.ClassSorterNode;

public final class ClassSelectMember extends ClassFilterExtension<ClassSelectMember> implements Iterable<Object> {

	private final ClassStorage<?> dataAspect;
	private final int ordinal;
	private final ClassStorageEngine engine;
	private ClassSorterNode sortFirst;
	private ClassSorterNode sortLast;
	private int pageSize = 1000;
	private int pageOrdinal;

	public ClassSelectMember(ClassStorage<?> aspect, int ordinal, ClassStorageEngine engine) {
		super(aspect);
		this.dataAspect = aspect;
		this.ordinal = ordinal;
		this.engine = engine;
	}

	public ClassSelectMember(ClassStorage<?> aspect, String name, ClassStorageEngine engine) {
		this(aspect, aspect.getOrdinalOrException(name), engine);
	}

	public final int getOrdinal() {
		return this.ordinal;
	}

	public final String getMemberDataName() {
		return this.dataAspect.getDataName(this.ordinal);
	}

	@Override
	protected final ClassSelectMember getThis() {
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

	public final ClassSelectMember page(int pageSize, int pageOrdinal) {
		this.setPageSize(pageSize);
		this.setPageOrdinal(pageOrdinal);
		return this;
	}

	public final ClassSelectMember orderBy(int ordinal, ClassSorterDirection direction) {
		if (this.sortFirst == null) {
			this.sortFirst = new ClassSorterNode(dataAspect, ordinal, direction);
			this.sortLast = this.sortFirst;
		} else {
			this.sortLast = this.sortLast.setNext(ordinal, direction);
		}
		return this;
	}

	public final ClassSelectMember orderBy(String name, ClassSorterDirection direction) {
		return orderBy(dataAspect.getOrdinalOrException(name), direction);
	}

	public final ClassSelectMember orderBy(ClassSorterDirection direction, String... names) {
		for (int i = 0; i < names.length; i++)
			orderBy(names[i], direction);
		return this;
	}

	public final ClassSelectMember orderBy(String... names) {
		return orderBy(ClassSorterDirection.Ascending, names);
	}

	public final List<Object> toList() {
		return this.engine.selectMembers(this.dataAspect, this.ordinal, this.getWhere(), this.sortFirst, this.pageSize,
				this.pageOrdinal);
	}

	@Override
	public final Iterator<Object> iterator() {
		return toList().iterator();
	}

	public final Object singleResult() {
		return this.engine.selectMember(this.dataAspect, this.ordinal, this.getWhere(), this.sortFirst);
	}
}
