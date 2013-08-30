package com.eixox.adapters;

public class FloatAdapter implements ValueAdapter<Float> {

	@Override
	public final Float adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Float)
			return (Float) input;
		else if (input instanceof Number)
			return ((Number) input).floatValue();
		else
			return Float.parseFloat(input.toString());
	}

	@Override
	public final Float parse(String input) {
		return Float.parseFloat(input);
	}

	@Override
	public final String format(Float input) {
		return input.toString();
	}

}
