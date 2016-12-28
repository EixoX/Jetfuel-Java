package com.eixox.restrictions;

import java.util.regex.Pattern;

public class EmailRestriction implements Restriction {

	public EmailRestriction() {
	}

	public EmailRestriction(Email email) {
		// just complying to a constructor pattern.
	}

	public static final Pattern rfc2822 = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	public static boolean isValid(String email) {
		return email != null && !email.isEmpty() && rfc2822.matcher(email.toLowerCase()).matches();
	}

	public boolean validate(Object input) {

		if (input == null)
			return true;

		String email = input.toString();
		if (email.isEmpty())
			return true;

		email = email.toLowerCase();

		if (!rfc2822.matcher(email).matches())
			return false;

		if (email.indexOf('+') >= 0)
			return false;

		int digitCount = 0;
		int letterCount = 0;
		@SuppressWarnings("unused")
		int otherCount = 0;
		int apos = email.indexOf('@');
		for (int i = 0; i < apos; i++) {
			int ctype = Character.getType(email.charAt(i));
			if (ctype == Character.LOWERCASE_LETTER)
				letterCount++;
			else if (ctype == Character.DECIMAL_DIGIT_NUMBER)
				digitCount++;
			else
				otherCount++;
		}

		if (letterCount == 0)
			return false;

		return digitCount < 7 || letterCount > digitCount;

	}

	public String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "Endereco de e-mail invalido";
	}

	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RuntimeException(msg);

	}

}
