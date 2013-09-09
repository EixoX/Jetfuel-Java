package com.eixox.restrictions;

public class LengthRestriction implements Restriction {

	private final int _Min;
	private final int _Max;

	public LengthRestriction(int min, int max) {
		this._Min = min;
		this._Max = max;
	}

	public LengthRestriction(Length length) {
		this(length.min(), length.max());
	}

	public final int getMin() {
		return this._Min;
	}

	public final int getMax() {
		return this._Max;
	}

	public static boolean isValid(String input, int min, int max) {
		if (input == null)
			return false;

		int length = input.length();
		return length >= min && length <= max;
	}

	
	public final boolean validate(Object input) {
		if (input == null)
			return false;

		int length = input.toString().length();
		return length >= _Min && length <= _Max;
	}

	
	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : ("Invalid length [" + _Min + ", " + _Max + "]");
	}

	
	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);
	}
}
