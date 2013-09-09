package com.eixox.adapters;

public class DoubleAdapter implements ValueAdapter<Double> {

	
	public final Double adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Double)
			return ((Double) input);
		else if (input instanceof Number)
			return ((Number) input).doubleValue();
		else
			return Double.parseDouble(input.toString());
	}

	
	public final Double parse(String input) {
		return Double.parseDouble(input);
	}

	
	public final String format(Double input) {
		return input.toString();
	}

}
