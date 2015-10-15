package com.eixox.restrictions;

public class NameRestriction implements Restriction {

	public boolean validate(Object input) {
		return isValid(input);
	}

	public String getRestrictionMessageFor(Object input) {
		if (validate(input))
			return null;
		else
			return "O valor informado não é um nome válido.";
	}

	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null && !msg.isEmpty())
			throw new RuntimeException(msg);
	}

	public static final boolean isValid(Object obj) {
		if (obj == null)
			return true;

		String str = obj.toString();

		if (str.isEmpty())
			return true;

		int l = str.length();
		for (int i = 0; i < l; i++) {
			char c = str.charAt(i);
			if (!Character.isLetterOrDigit(c))
				if (c != ' ' && c != '\'' && c != '-')
					return false;
		}

		return true;

	}

}
