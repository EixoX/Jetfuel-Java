package com.eixox.sorters;

import com.eixox.reflection.Aspect;

public class ClassSorterNode {

	private final Aspect			aspect;
	private final int				ordinal;
	private ClassSorterDirection	direction;
	private ClassSorterNode			next;

	public ClassSorterNode(Aspect aspect, int ordinal,
			ClassSorterDirection direction) {
		this.aspect = aspect;
		this.ordinal = ordinal;
		this.direction = direction;
	}

	public ClassSorterNode(Aspect aspect, int ordinal) {
		this(aspect, ordinal, ClassSorterDirection.Ascending);
	}

	public final ClassSorterDirection getDirection() {
		return direction;
	}

	public final void setDirection(ClassSorterDirection direction) {
		this.direction = direction;
	}

	public final ClassSorterNode getNext() {
		return next;
	}

	public final void setNext(ClassSorterNode next) {
		this.next = next;
	}

	public final Aspect getAspect() {
		return aspect;
	}

	public final int getOrdinal() {
		return ordinal;
	}

}
