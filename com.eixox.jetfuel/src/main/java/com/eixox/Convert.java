package com.eixox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public final class Convert {

	public static final Object changeType(Class<?> claz, Object input, Locale locale) {

		if (input == null || input.getClass() == claz)
			return input;

		if (claz == Byte.TYPE || claz == Byte.class) {
			return (input instanceof Number) ? ((Number) input).byteValue() : Byte.parseByte(input.toString());
		}

		if (claz == Character.TYPE || claz == Character.class)
			return new Character(input.toString().charAt(0));

		if (claz == Short.TYPE || claz == Short.class) {
			return (input instanceof Number) ? ((Number) input).shortValue() : Short.parseShort(input.toString());
		}

		if (claz == Integer.TYPE || claz == Integer.class) {
			return (input instanceof Number) ? ((Number) input).intValue() : Integer.parseInt(input.toString());
		}

		if (claz == Long.TYPE || claz == Long.class) {
			return (input instanceof Number) ? ((Number) input).longValue() : Long.parseLong(input.toString());
		}

		if (claz == Float.TYPE || claz == Float.class) {
			return (input instanceof Number) ? ((Number) input).floatValue() : Float.parseFloat(input.toString());
		}

		if (claz == Double.TYPE || claz == Double.class) {
			return (input instanceof Number) ? ((Number) input).doubleValue() : Double.parseDouble(input.toString());
		}

		if (Calendar.class.isAssignableFrom(claz)) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
				Date dt = sdf.parse(input.toString());
				GregorianCalendar gregorianCalendar = new GregorianCalendar();
				gregorianCalendar.setTime(dt);
				return gregorianCalendar;
			} catch (ParseException e) {
				throw new RuntimeException(input.toString(), e);
			}
		}

		throw new RuntimeException("Can't convert " + claz);
	}

	public static final long toLong(Object input) {
		if (input == null)
			return 0L;

		if (input instanceof Long)
			return ((Long) input);

		if (input instanceof Number)
			return ((Number) input).longValue();

		if (input instanceof String)
			return Long.parseLong((String) input);

		return (Long) input;
	}

	public static int tryParseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (Exception e) {
			return 0;
		}
	}

	private static final DateFormat rfc2822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz",
			Locale.ENGLISH);

	public static final Date toDateRfc2822(String input) throws ParseException {

		if (input == null || input.isEmpty())
			return null;
		else
			return rfc2822DateFormat.parse(input);

	}
	
}
