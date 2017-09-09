package com.eixox.restrictions;


public class InStringListRestriction implements Restriction {

	private final String[] values;

	public InStringListRestriction(String... values) {
		this.values = values;
	}

	public InStringListRestriction(InStringList annotation) {
		this(annotation.values());
	}

	public final String[] getValues() {
		return this.values;
	}

	public final boolean validate(Object input) {
		if (input == null)
			return true;

		String s = input.toString();
		if (s.isEmpty())
			return true;

		for (int i = 0; i < values.length; i++)
			if (s.equalsIgnoreCase(values[i]))
				return true;

		return false;

	}

	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Este nao e um valor permitido";
	}

	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);
	}

}
