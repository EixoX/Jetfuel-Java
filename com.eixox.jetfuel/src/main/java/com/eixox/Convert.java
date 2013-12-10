package com.eixox;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public final class Convert {

	public static final DateFormat rfc2822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
	public static final DateFormat defaultDateFormat = SimpleDateFormat.getInstance();
	public static final DateFormat dateTimeInternational = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat javaFuckup = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

	// ___________________________________________________________
	public static final boolean toBoolean(Object value) {
		if (value == null)
			return false;
		else if (value instanceof Boolean)
			return ((Boolean) value);
		else if (value instanceof Number)
			return ((Number) value).intValue() > 0;
		else
			return Boolean.parseBoolean(value.toString());
	}

	// ___________________________________________________________
	public static final byte toByte(Object input) {
		if (input == null)
			return (byte) 0;
		else if (input instanceof Byte)
			return ((Byte) input);
		else if (input instanceof Number)
			return ((Number) input).byteValue();
		else
			return Byte.parseByte(input.toString());
	}

	// ___________________________________________________________
	public static final char toCharacter(Object input) {
		if (input == null)
			return (char) 0;
		else if (input instanceof Character)
			return ((Character) input);
		else if (input instanceof Number)
			return (char) ((Number) input).intValue();
		else
			return input.toString().charAt(0);
	}

	// ___________________________________________________________
	public static final short toShort(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Short)
			return ((Short) input);
		else if (input instanceof Number)
			return ((Number) input).shortValue();
		else
			return Short.parseShort(input.toString());
	}

	// ___________________________________________________________
	public static final int toInt(Object input) {
		if (input == null)
			return 0;
		else if (input instanceof Integer)
			return ((Integer) input);
		else if (input instanceof Number)
			return ((Number) input).intValue();
		else if (input instanceof String && ((String) input).isEmpty())
			return 0;
		else
			return Integer.parseInt(input.toString());
	}

	// ___________________________________________________________
	public static final long toLong(Object input) {
		if (input == null)
			return 0L;
		else if (input instanceof Long)
			return ((Long) input);
		else if (input instanceof Number)
			return ((Number) input).longValue();
		else
			return Long.parseLong(input.toString());
	}

	// ___________________________________________________________
	public static final float toFloat(Object input) {
		if (input == null)
			return 0F;
		else if (input instanceof Float)
			return ((Float) input);
		else if (input instanceof Number)
			return ((Number) input).floatValue();
		else
			return Float.parseFloat(input.toString());
	}

	// ___________________________________________________________
	public static final float toFloat(Object input, Locale locale) {
		if (input == null)
			return 0F;
		else if (input instanceof Float)
			return ((Float) input);
		else if (input instanceof Number)
			return ((Number) input).floatValue();
		else
			try {
				return DecimalFormat.getInstance(locale).parse(input.toString()).floatValue();
			} catch (ParseException e) {
				return 0F;
			}
	}

	// ___________________________________________________________
	public static final double toDouble(Object input) {
		if (input == null)
			return 0.0;
		else if (input instanceof Double)
			return ((Double) input);
		else if (input instanceof Number)
			return ((Number) input).doubleValue();
		else
			return Double.parseDouble(input.toString());
	}

	// ___________________________________________________________
	public static final double toDouble(Object input, Locale locale) {
		if (input == null)
			return 0.0;
		else if (input instanceof Double)
			return ((Double) input);
		else if (input instanceof Number)
			return ((Number) input).doubleValue();
		else
			try {
				return DecimalFormat.getInstance(locale).parse(input.toString()).doubleValue();
			} catch (ParseException e) {
				return 0.0;
			}
	}

	// ___________________________________________________________
	public static final Date toDateRfc2822(String input) throws ParseException {

		if (input == null || input.isEmpty())
			return null;
		else
			return rfc2822DateFormat.parse(input);
	}

	// ___________________________________________________________
	public static final Date toDate(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Date)
			return ((Date) value);
		else if (value instanceof Calendar)
			return ((Calendar) value).getTime();
		else
			try {
				return defaultDateFormat.parse(value.toString());
			} catch (ParseException pe) {
				try {
					return dateTimeInternational.parse(value.toString());
				} catch (ParseException pe2) {
					try {
						return rfc2822DateFormat.parse(value.toString());
					} catch (ParseException pe3) {
						try {
							return javaFuckup.parse(value.toString());
						} catch (ParseException pe4) {
							return null;
						}
					}
				}
			}
	}

	// ___________________________________________________________
	public static final Calendar toCalendar(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Calendar)
			return ((Calendar) value);
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(toDate(value));
			return cal;
		}
	}

	// ___________________________________________________________
	public static final Object changeType(Class<?> claz, Object input, Locale locale) {

		if (input == null || claz.isAssignableFrom(input.getClass()))
			return input;

		if (claz == Boolean.TYPE || claz == Boolean.class)
			return toBoolean(input);

		if (claz == Byte.TYPE || claz == Byte.class)
			return toByte(input);

		if (claz == Character.TYPE || claz == Character.class)
			return toCharacter(input);

		if (claz == Short.TYPE || claz == Short.class)
			return toShort(input);

		if (claz == Integer.TYPE || claz == Integer.class)
			return toInt(input);

		if (claz == Long.TYPE || claz == Long.class)
			return toLong(input);

		if (claz == Float.TYPE || claz == Float.class)
			return toFloat(input, locale);

		if (claz == Double.TYPE || claz == Double.class)
			return toDouble(input, locale);

		if (Date.class.isAssignableFrom(claz))
			return toDate(input);

		if (Calendar.class.isAssignableFrom(claz))
			return toCalendar(input);

		throw new RuntimeException("Can't convert " + claz);
	}

	// ___________________________________________________________
	public static int tryParseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			return 0;
		}
	}

}
