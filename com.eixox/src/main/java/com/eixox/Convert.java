package com.eixox;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.eixox.adapters.ValueAdapters;

public final class Convert {

	public static final boolean toBoolean(Object input) {
		return toBoolean(input, false);
	}

	public static final boolean toBoolean(Object input, boolean defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Boolean)
			return ((Boolean) input);
		else if (Boolean.TYPE.isInstance(input))
			return ((Boolean) input);
		else if (input instanceof Number)
			return ((Number) input).intValue() > 0;
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Boolean.parseBoolean((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Boolean");
	}

	public static final int toInteger(Object input) {
		return toInteger(input, 0);
	}

	public static final int toInteger(Object input, int defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Integer)
			return ((Integer) input);
		else if (Integer.TYPE.isInstance(input))
			return ((Integer) input);
		else if (input instanceof Number)
			return ((Number) input).intValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Integer.parseInt((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Integer");
	}

	public static final long toLong(Object input) {
		return toLong(input, 0L);
	}

	public static final long toLong(Object input, long defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Long)
			return ((Long) input);
		else if (Long.TYPE.isInstance(input))
			return ((Long) input);
		else if (input instanceof Number)
			return ((Number) input).longValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Long.parseLong((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Long");
	}

	public static final double toDouble(Object input) {
		return toDouble(input, 0.0);
	}

	public static final double toDouble(Object input, double defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Double)
			return ((Double) input);
		else if (Double.TYPE.isInstance(input))
			return ((Double) input);
		else if (input instanceof Number)
			return ((Number) input).doubleValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Double.parseDouble((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Double");
	}

	public static final float toFloat(Object input) {
		return toFloat(input, 0.0F);
	}

	public static final float toFloat(Object input, float defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Float)
			return ((Float) input);
		else if (Float.TYPE.isInstance(input))
			return ((Float) input);
		else if (input instanceof Number)
			return ((Number) input).floatValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Float.parseFloat((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Float");
	}

	public static final byte toByte(Object input) {
		return toByte(input, (byte) 0);
	}

	public static final byte toByte(Object input, byte defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Byte)
			return ((Byte) input);
		else if (Byte.TYPE.isInstance(input))
			return ((Byte) input);
		else if (input instanceof Number)
			return ((Number) input).byteValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Byte.parseByte((String) input);
		} else
			throw new RuntimeException("Can't convert " + input + " to Byte");
	}

	public static final char toCharacter(Object input) {
		return toCharacter(input, (char) 0);
	}

	public static final char toCharacter(Object input, char defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Character)
			return ((Character) input);
		else if (Character.TYPE.isInstance(input))
			return ((Character) input);
		else if (input instanceof Number)
			return (char) ((Number) input).intValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return ((String) input).charAt(0);
		} else
			throw new RuntimeException("Can't convert " + input + " to Character");
	}

	public static final short toShort(Object input) {
		return toShort(input, (short) 0);
	}

	public static final short toShort(Object input, short defaultValue) {
		if (input == null)
			return defaultValue;
		else if (input instanceof Short)
			return ((Short) input);
		else if (Short.TYPE.isInstance(input))
			return ((Short) input);
		else if (input instanceof Number)
			return ((Number) input).shortValue();
		else if (input instanceof String) {
			if (((String) input).isEmpty())
				return defaultValue;
			else
				return Short.parseShort((String) input);
		} else
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

	public static final Date toDate(Object value, int dateFormat, Locale locale) throws ParseException {
		if (value == null)
			return null;
		else if (value instanceof Date)
			return (Date) value;
		else if (value instanceof Calendar)
			return ((Calendar) value).getTime();
		else if (value instanceof String) {
			String s = (String) value;
			return DateFormat.getDateInstance(dateFormat, locale).parse(s);
		} else
			throw new RuntimeException("Can't convert to Date the :" + value.toString());
	}

	public static final boolean isNullOrEmpty(Object instance) {
		if (instance == null)
			return true;
		else if (instance instanceof String)
			return ((String) instance).isEmpty();
		else if (instance instanceof Number)
			return ((Number) instance).doubleValue() == 0.0;
		else
			return false;

	}
}
