package com.eixox.restrictions;

public class PositiveIntegerRestriction implements Restriction {

	public PositiveIntegerRestriction() {
	}

	public PositiveIntegerRestriction(PositiveInteger annotation) {
	}

	public final boolean validate(Object input) {
		if (input == null)
			return true;
		else if (Number.class.isAssignableFrom(input.getClass()))
			return true;
		else {
			String st = input.toString();
			int l = st.length();
			for (int i = 0; i < l; i++)
				if (!Character.isDigit(st.charAt(i)))
					return false;
		}
		return true;
	}

	public final String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Valor não é numérico";
	}

	public final void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
