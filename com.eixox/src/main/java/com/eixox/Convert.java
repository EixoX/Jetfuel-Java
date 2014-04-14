package com.eixox;

import com.eixox.adapters.ValueAdapters;

public final class Convert {

	public static final boolean toBoolean(Object input) {
		if (input == null)
			return false;
		else if (input instanceof Boolean)
			return ((Boolean) input);
		else if (Boolean.TYPE.isInstance(input))
			return ((Boolean) input);
		else if (input instanceof Number)
			return ((Number) input).intValue() > 0;
		else if (input instanceof String)
			return Boolean.parseBoolean((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Boolean");
	}

	public static final int toInteger(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Integer)
			return ((Integer) input);
		else if (Integer.TYPE.isInstance(input))
			return ((Integer) input);
		else if (input instanceof Number)
			return ((Number) input).intValue();
		else if (input instanceof String)
			return Integer.parseInt((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Integer");
	}

	public static final long toLong(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Long)
			return ((Long) input);
		else if (Long.TYPE.isInstance(input))
			return ((Long) input);
		else if (input instanceof Number)
			return ((Number) input).longValue();
		else if (input instanceof String)
			return Long.parseLong((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Long");
	}

	public static final double toDouble(Object input) {
		if (input == null)
			return 0.0;
		else if (input instanceof Double)
			return ((Double) input);
		else if (Double.TYPE.isInstance(input))
			return ((Double) input);
		else if (input instanceof Number)
			return ((Number) input).doubleValue();
		else if (input instanceof String)
			return Double.parseDouble((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Double");
	}

	public static final float toFloat(Object input) {
		if (input == null)
			return 0.0F;
		else if (input instanceof Float)
			return ((Float) input);
		else if (Float.TYPE.isInstance(input))
			return ((Float) input);
		else if (input instanceof Number)
			return ((Number) input).floatValue();
		else if (input instanceof String)
			return Float.parseFloat((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Float");
	}

	public static final byte toByte(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Byte)
			return ((Byte) input);
		else if (Byte.TYPE.isInstance(input))
			return ((Byte) input);
		else if (input instanceof Number)
			return ((Number) input).byteValue();
		else if (input instanceof String)
			return Byte.parseByte((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Byte");
	}

	public static final char toCharacter(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Character)
			return ((Character) input);
		else if (Character.TYPE.isInstance(input))
			return ((Character) input);
		else if (input instanceof Number)
			return (char) ((Number) input).intValue();
		else if (input instanceof String)
			return ((String) input).charAt(0);
		else
			throw new RuntimeException("Can't convert " + input + " to Character");
	}

	public static final short toShort(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Short)
			return ((Short) input);
		else if (Short.TYPE.isInstance(input))
			return ((Short) input);
		else if (input instanceof Number)
			return ((Number) input).shortValue();
		else if (input instanceof String)
			return Short.parseShort((String) input);
		else
			throw new RuntimeException("Can't convert " + input + " to Short");
	}

	public static final Object changeType(Object value, Class<?> expectedType) {
		if (value == null)
			return null;
		else if (expectedType.isInstance(value))
			return value;
		else
			return ValueAdapters.getAdapter(expectedType).convert(value);
	}
}
