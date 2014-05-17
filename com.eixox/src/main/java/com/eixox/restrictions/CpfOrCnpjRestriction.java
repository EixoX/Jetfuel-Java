package com.eixox.restrictions;

public class CpfOrCnpjRestriction implements Restriction {

	public CpfOrCnpjRestriction() {
	}

	public CpfOrCnpjRestriction(CpfOrCnpj CpfOrCnpj) {
		// just complying to a constructor pattern.
	}

	public static boolean isValid(long value) {
		return CpfRestriction.isValid(value) || CnpjRestriction.isValid(value);
	}

	public boolean validate(Object input) {
		if (input == null)
			return true;
		else if (input instanceof String) {
			String is = (String) input;
			if (is.isEmpty())
				return true;
			else
				return isValid(Long.parseLong(is));
		} else if (input instanceof Number)
			return isValid(((Number) input).longValue());
		else
			return false;
	}

	public String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Cpf ou Cnpj inválido";
	}

	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}
}
