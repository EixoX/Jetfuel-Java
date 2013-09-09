package com.eixox.adapters;

public class LongAdapter implements ValueAdapter<Long> {

	
	public final Long adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Long)
			return (Long) input;
		else if (input instanceof Number)
			return ((Number) input).longValue();
		else
			return Long.parseLong(input.toString());

	}

	
	public final Long parse(String input) {
		return Long.parseLong(input);
	}

	
	public final String format(Long input) {
		return input.toString();
	}

}
