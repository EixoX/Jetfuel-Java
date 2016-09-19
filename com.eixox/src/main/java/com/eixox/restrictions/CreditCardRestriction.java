package com.eixox.restrictions;

public class CreditCardRestriction implements Restriction {

	public boolean validate(Object input) {
		if (input == null || "".equals(input))
			return true;
		else
			return isValid(input.toString());
	}

	public String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Não é um cartão de crédito válido";

	}

	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null && !msg.isEmpty())
			throw new RestrictionException(msg);

	}

	public static final boolean isValid(String digits) {

		if (digits == null || digits.isEmpty())
			return false;

		int length = digits.length();

		if (length < 12 || length > 19)
			return false;

		//Quick conversion to int and validation.
		int[] digitsReversed = new int[length];
		for (int i = 0; i < digitsReversed.length; i++) {
			digitsReversed[i] = parseInt(digits.charAt(length - i - 1));
			if (digitsReversed[i] < 0)
				return false;
		}

		int luhn = 0;
		for (int i = 0; i < digitsReversed.length; i++) {
			int d = i % 2 == 1 ? 2 * digitsReversed[i] : digitsReversed[i];
			luhn += (d / 10) + (d % 10);
		}
		return (luhn != 0) && (luhn % 10 == 0);
	}

	private static final int parseInt(char c) {
		switch (c) {
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case '0':
			return 0;
		default:
			return -1;
		}
	}

}
