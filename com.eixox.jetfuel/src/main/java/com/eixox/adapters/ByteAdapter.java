package com.eixox.adapters;

public class ByteAdapter implements ValueAdapter<Byte> {

	
	public final Byte adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Byte)
			return (Byte) input;
		else if (input instanceof Number)
			return ((Number) input).byteValue();
		else
			return Byte.parseByte(input.toString());
	}

	
	public final Byte parse(String input) {
		return Byte.parseByte(input);
	}

	
	public final String format(Byte input) {
		return input.toString();
	}

}
