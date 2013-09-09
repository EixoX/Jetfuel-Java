package com.eixox.restrictions;

public class MaxLengthRestriction implements Restriction {

	public static boolean isValid(String input, int maxlength) {
		return input != null && input.length() <= maxlength;
	}

	private final int _Value;

	public MaxLengthRestriction(int value) {
		this._Value = value;
	}

	public MaxLengthRestriction(MaxLength maxLength) {
		this(maxLength.value());
	}

	public final int getValue() {
		return this._Value;
	}

	
	public final boolean validate(Object input) {
		return input == null || input.toString().length() < _Value;
	}

	
	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Text invalid or too large for max length " + this._Value;
	}

	
	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
