package com.eixox.reflection;

public class AspectMemberValue {

	private final Aspect _Aspect;
	private final int _Ordinal;
	private final Object _Value;

	public AspectMemberValue(Aspect aspect, int ordinal, Object value) {
		this._Aspect = aspect;
		this._Ordinal = ordinal;
		this._Value = value;
	}

	public AspectMemberValue(Aspect aspect, String name, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), value);
	}

	public final Aspect getAspect() {
		return this._Aspect;
	}

	public final int getOrdinal() {
		return this._Ordinal;
	}

	public final Object getValue() {
		return this._Value;
	}
}
