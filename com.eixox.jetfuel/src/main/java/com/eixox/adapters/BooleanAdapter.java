package com.eixox.adapters;

public class BooleanAdapter implements ValueAdapter<Boolean> {

	@Override
	public final Boolean adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Boolean)
			return (Boolean) input;
		else if (input instanceof Number)
			return ((Number) input).intValue() != 0;
		else
			return Boolean.parseBoolean(input.toString());
	}

	@Override
	public final Boolean parse(String input) {
		return Boolean.parseBoolean(input);
	}

	@Override
	public final String format(Boolean input) {
		return input.toString();
	}

}
