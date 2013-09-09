package com.eixox.adapters;

public class StringAdapter implements ValueAdapter<String> {

	
	public final String adapt(Object input) {
		if (input == null)
			return null;
		else
			return input.toString();
	}

	
	public final String parse(String input) {
		return input;
	}

	
	public final String format(String input) {
		return input;
	}

}
