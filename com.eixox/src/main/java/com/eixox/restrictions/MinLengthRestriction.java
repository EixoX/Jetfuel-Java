package com.eixox.restrictions;

public class MinLengthRestriction implements Restriction {

	public static boolean isValid(String input, int Minlength) {
		return input != null && input.length() <= Minlength;
	}

	private final int _Value;

	public MinLengthRestriction(int value) {
		this._Value = value;
	}

	public MinLengthRestriction(MinLength MinLength) {
		this(MinLength.value());
	}

	public final int getValue() {
		return this._Value;
	}

	
	public final boolean validate(Object input) {
		return input != null && input.toString().length() >= this._Value;
	}

	
	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Texto muito pequeno. Min " + this._Value;
	}

	
	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
