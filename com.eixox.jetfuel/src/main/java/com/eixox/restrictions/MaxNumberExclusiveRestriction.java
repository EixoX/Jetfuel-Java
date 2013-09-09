package com.eixox.restrictions;

public class MaxNumberExclusiveRestriction implements Restriction {

	private final double _Value;

	public MaxNumberExclusiveRestriction(double value) {
		this._Value = value;
	}

	public MaxNumberExclusiveRestriction(MaxNumberExclusive maxNumberExclusive) {
		this(maxNumberExclusive.value());
	}

	public final double getValue() {
		return this._Value;
	}

	
	public final boolean validate(Object input) {
		if (input == null || !(input instanceof Number))
			return false;
		else
			return ((Number) input).doubleValue() < _Value;
	}

	
	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Value invalid or above maximum (exclusive) " + _Value;
	}

	
	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
