package com.eixox.adapters;

public class CharAdapter implements ValueAdapter<Character> {

	
	public final Character adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Character)
			return ((Character) input);
		else if (input instanceof Number)
			return new Character((char) ((Number) input).intValue());
		else
			return input.toString().charAt(0);
	}

	
	public final Character parse(String input) {
		return input == null || input.length() < 1 ? Character.MIN_VALUE : input.charAt(0);
	}

	
	public final String format(Character input) {
		return input.toString();
	}

}
