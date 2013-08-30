package com.eixox.filters;

import com.eixox.reflection.Aspect;

public class ClassFilterExpression implements ClassFilter {

	private final Aspect _Aspect;
	private final ClassFilterNode _First;
	private ClassFilterNode _Last;

	public ClassFilterExpression(ClassFilter filter) {
		this._Aspect = filter.getAspect();
		this._First = new ClassFilterNode(filter);
		this._Last = this._First;
	}

	public ClassFilterExpression(Aspect aspect, int ordinal,
			ClassFilterComparison comparison, Object value) {
		this(new ClassFilterTerm(aspect, ordinal, comparison, value));
	}

	public ClassFilterExpression(Aspect aspect, int ordinal, Object value) {
		this(new ClassFilterTerm(aspect, ordinal, value));
	}

	public ClassFilterExpression(Aspect aspect, String name,
			ClassFilterComparison comparison, Object value) {
		this(new ClassFilterTerm(aspect, name, comparison, value));
	}

	public ClassFilterExpression(Aspect aspect, String name, Object value) {
		this(new ClassFilterTerm(aspect, name, value));
	}

	public final ClassFilterNode getFirst() {
		return this._First;
	}

	@Override
	public final Aspect getAspect() {
		return this._Aspect;
	}

	public final ClassFilterExpression and(ClassFilter filter) {
		this._Last = this._Last.setNext(ClassFilterOperation.And, filter);
		return this;
	}

	public final ClassFilterExpression and(int ordinal,
			ClassFilterComparison comparison, Object value) {
		return and(new ClassFilterTerm(_Aspect, ordinal, comparison, value));
	}

	public final ClassFilterExpression and(int ordinal, Object value) {
		return and(new ClassFilterTerm(_Aspect, ordinal, value));
	}

	public final ClassFilterExpression and(String name,
			ClassFilterComparison comparison, Object value) {
		return and(new ClassFilterTerm(_Aspect, name, comparison, value));
	}

	public final ClassFilterExpression and(String name, Object value) {
		return and(new ClassFilterTerm(_Aspect, name, value));
	}

	public final ClassFilterExpression or(ClassFilter filter) {
		this._Last = this._Last.setNext(ClassFilterOperation.Or, filter);
		return this;
	}

	public final ClassFilterExpression or(int ordinal,
			ClassFilterComparison comparison, Object value) {
		return or(new ClassFilterTerm(_Aspect, ordinal, comparison, value));
	}

	public final ClassFilterExpression or(int ordinal, Object value) {
		return or(new ClassFilterTerm(_Aspect, ordinal, value));
	}

	public final ClassFilterExpression or(String name,
			ClassFilterComparison comparison, Object value) {
		return or(new ClassFilterTerm(_Aspect, name, comparison, value));
	}

	public final ClassFilterExpression or(String name, Object value) {
		return or(new ClassFilterTerm(_Aspect, name, value));
	}
}
