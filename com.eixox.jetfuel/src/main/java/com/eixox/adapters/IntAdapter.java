package com.eixox.adapters;

public class IntAdapter implements ValueAdapter<Integer> {

	
	public final Integer adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Integer)
			return (Integer) input;
		else if (input instanceof Number)
			return ((Number) input).intValue();
		else
			return Integer.parseInt(input.toString());

	}

	
	public final Integer parse(String input) {
		return Integer.parseInt(input);
	}

	
	public final String format(Integer input) {
		return input.toString();
	}

}
