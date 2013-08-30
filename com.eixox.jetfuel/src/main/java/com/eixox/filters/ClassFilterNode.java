package com.eixox.filters;

import com.eixox.reflection.Aspect;

public class ClassFilterNode implements ClassFilter {

	private final ClassFilter _Filter;
	private ClassFilterOperation _Operation;
	private ClassFilterNode _Next;

	public ClassFilterNode(ClassFilter filter) {
		this._Filter = filter;
	}

	public final ClassFilter getFilter() {
		return this._Filter;
	}

	public final ClassFilterOperation getOperation() {
		return this._Operation;
	}

	public final ClassFilterNode getNext() {
		return this._Next;
	}

	public final ClassFilterNode setNext(ClassFilterOperation operation,
			ClassFilter next) {
		this._Operation = operation;
		this._Next = new ClassFilterNode(next);
		return this._Next;
	}

	@Override
	public final Aspect getAspect() {
		return _Filter.getAspect();
	}
}
