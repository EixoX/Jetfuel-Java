package com.eixox.restrictions;

public class MinNumberInclusiveRestriction implements Restriction {

	private final double _Value;

	public MinNumberInclusiveRestriction(double value) {
		this._Value = value;
	}

	public MinNumberInclusiveRestriction(MinNumberInclusive MinNumberInclusive) {
		this(MinNumberInclusive.value());
	}

	public final double getValue() {
		return this._Value;
	}

	
	public final boolean validate(Object input) {
		if (input == null || !(input instanceof Number))
			return false;
		else
			return ((Number) input).doubleValue() >= _Value;
	}

	
	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Value invalid or below Minimum (Inclusive) " + _Value;
	}

	
	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
