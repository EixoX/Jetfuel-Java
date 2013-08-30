package com.eixox.adapters;

public class StringAdapter implements ValueAdapter<String> {

	@Override
	public final String adapt(Object input) {
		if (input == null)
			return null;
		else
			return input.toString();
	}

	@Override
	public final String parse(String input) {
		return input;
	}

	@Override
	public final String format(String input) {
		return input;
	}

}
