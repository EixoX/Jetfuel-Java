package com.eixox.sorters;

import com.eixox.reflection.Aspect;

public class ClassSorterNode {

	private final Aspect _Aspect;
	private final int _Ordinal;
	private ClassSorterDirection _Direction;
	private ClassSorterNode _Next;

	public ClassSorterNode(Aspect aspect, int ordinal,
			ClassSorterDirection direction) {
		this._Aspect = aspect;
		this._Ordinal = ordinal;
		this._Direction = direction;
	}

	public ClassSorterNode(Aspect aspect, int ordinal) {
		this(aspect, ordinal, ClassSorterDirection.Ascending);
	}

	public final Aspect getAspect() {
		return this._Aspect;
	}

	public final int getOrdinal() {
		return this._Ordinal;
	}

	public final ClassSorterDirection getDirection() {
		return this._Direction;
	}

	public final ClassSorterNode getNext() {
		return this._Next;
	}

	public final ClassSorterNode setNext(int ordinal,
			ClassSorterDirection direction) {
		this._Next = new ClassSorterNode(this._Aspect, ordinal, direction);
		return this._Next;
	}
}
