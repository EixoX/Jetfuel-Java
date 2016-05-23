package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public final class LongAdapter implements ValueAdapter<Long> {

	public final NumberFormat numberFormat;

	public LongAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public LongAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public LongAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Long> getDataType() {
		return Long.TYPE;
	}

	public final String format(Long input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Long parse(String input) {
		return parseLong(input);
	}

	public final long parseLong(String input) {
		if (input == null || input.isEmpty())
			return 0L;
		else
			try {
				return this.numberFormat.parse(input).longValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			long d = parseLong(source);
			field.setLong(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			long d = source.getLong(position);
			field.setLong(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			long d = source.getLong(name);
			field.setLong(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			long d = field.getLong(source);
			target.setLong(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Long source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}
	
	public final Long convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Long)
			return (Long) source;
		else if (source instanceof Number)
			return ((Number) source).longValue();
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Date)
			return ((Date) source).getTime();
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Long.");
	}
	

	public final String formatObject(Object value) {
		return format((Long) value);
	}


	public static final LongAdapter ENGLISH = new LongAdapter(Locale.ENGLISH);

}
