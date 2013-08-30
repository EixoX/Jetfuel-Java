package com.eixox.filters;

import com.eixox.reflection.Aspect;

public class ClassFilterTerm implements ClassFilter {

	private final Aspect _Aspect;
	private final int _Ordinal;
	private final ClassFilterComparison _Comparison;
	private final Object _Value;

	public ClassFilterTerm(Aspect aspect, int ordinal,
			ClassFilterComparison comparison, Object value) {
		this._Aspect = aspect;
		this._Ordinal = ordinal;
		this._Comparison = comparison;
		this._Value = value;
	}

	public ClassFilterTerm(Aspect aspect, int ordinal, Object value) {
		this(aspect, ordinal, ClassFilterComparison.EqualTo, value);
	}

	public ClassFilterTerm(Aspect aspect, String name,
			ClassFilterComparison comparison, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), comparison, value);
	}

	public ClassFilterTerm(Aspect aspect, String name, Object value) {
		this(aspect, aspect.getOrdinalOrException(name),
				ClassFilterComparison.EqualTo, value);
	}

	public final Aspect getAspect() {
		return this._Aspect;
	}

	public final int getOrdinal() {
		return this._Ordinal;
	}

	public final ClassFilterComparison getComparison() {
		return this._Comparison;
	}

	public final Object getValue() {
		return this._Value;
	}
}
