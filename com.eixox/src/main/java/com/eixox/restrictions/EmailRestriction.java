package com.eixox.restrictions;

import java.util.regex.Pattern;

public class EmailRestriction implements Restriction {

	public EmailRestriction() {
	}

	public EmailRestriction(Email email) {
		//just complying to a constructor pattern.
	}

	public static final Pattern rfc2822 = Pattern
			.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");

	public static boolean isValid(String email) {
		return email != null && !email.isEmpty() && rfc2822.matcher(email.toLowerCase()).matches();
	}

	
	public boolean validate(Object input) {
		return input == null || ((String) input).isEmpty() ? true : isValid(((String) input).toLowerCase());
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
