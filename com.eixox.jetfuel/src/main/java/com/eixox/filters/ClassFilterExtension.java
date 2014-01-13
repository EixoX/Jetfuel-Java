package com.eixox.filters;

import com.eixox.reflection.Aspect;

public abstract class ClassFilterExtension<TClass> {

	private final Aspect _Aspect;
	private ClassFilterNode _First;
	private ClassFilterNode _Last;

	protected abstract TClass getThis();

	public ClassFilterExtension(Aspect aspect) {
		this._Aspect = aspect;
	}

	public final ClassFilterNode getWhere() {
		return this._First;
	}

	public final TClass where(ClassFilter filter) {
		this._First = new ClassFilterNode(filter);
		this._Last = this._First;
		return getThis();
	}

	public final Aspect getAspect() {
		return this._Aspect;
	}

	public final TClass where(int ordinal, ClassFilterComparison comparison, Object value) {
		return where(new ClassFilterTerm(_Aspect, ordinal, comparison, value));
	}

	public final TClass where(int ordinal, Object value) {
		return where(new ClassFilterTerm(_Aspect, ordinal, value));
	}

	public final TClass where(String name, ClassFilterComparison comparison, Object value) {
		return where(new ClassFilterTerm(_Aspect, name, comparison, value));
	}

	public final TClass where(String name, Object value) {
		return where(new ClassFilterTerm(_Aspect, name, value));
	}

	public final TClass and(ClassFilter filter) {
		if (this._Last == null)
			return where(filter);
		else {
			this._Last = this._Last.setNext(ClassFilterOperation.And, filter);
			return getThis();
		}
	}

	public final TClass and(int ordinal, ClassFilterComparison comparison, Object value) {
		return and(new ClassFilterTerm(_Aspect, ordinal, comparison, value));
	}

	public final TClass and(int ordinal, Object value) {
		return and(new ClassFilterTerm(_Aspect, ordinal, value));
	}

	public final TClass and(String name, ClassFilterComparison comparison, Object value) {
		return and(new ClassFilterTerm(_Aspect, name, comparison, value));
	}

	public final TClass and(String name, Object value) {
		return and(new ClassFilterTerm(_Aspect, name, value));
	}

	public final TClass or(ClassFilter filter) {
		if (this._Last == null)
			return where(filter);
		else {
			this._Last = this._Last.setNext(ClassFilterOperation.Or, filter);
			return getThis();
		}
	}

	public final TClass or(int ordinal, ClassFilterComparison comparison, Object value) {
		return or(new ClassFilterTerm(_Aspect, ordinal, comparison, value));
	}

	public final TClass or(int ordinal, Object value) {
		return or(new ClassFilterTerm(_Aspect, ordinal, value));
	}

	public final TClass or(String name, ClassFilterComparison comparison, Object value) {
		return or(new ClassFilterTerm(_Aspect, name, comparison, value));
	}

	public final TClass or(String name, Object value) {
		return or(new ClassFilterTerm(_Aspect, name, value));
	}
}
