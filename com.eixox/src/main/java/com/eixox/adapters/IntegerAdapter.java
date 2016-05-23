package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public final class IntegerAdapter implements ValueAdapter<Integer> {

	public final NumberFormat numberFormat;

	public IntegerAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public IntegerAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public IntegerAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Integer> getDataType() {
		return Integer.TYPE;
	}

	public final String format(Integer input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Integer parse(String input) {
		return parseInteger(input);
	}

	public final int parseInteger(String input) {
		if (input == null || input.isEmpty())
			return 0;
		else
			try {
				return this.numberFormat.parse(input).intValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			int d = parseInteger(source);
			field.setInt(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			int d = source.getInt(position);
			field.setInt(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			int d = source.getInt(name);
			field.setInt(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			int d = field.getInt(source);
			target.setInt(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Integer source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}
	
	public final Integer convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Integer)
			return (Integer) source;
		else if (source instanceof Number)
			return ((Number) source).intValue();
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Date)
			return (int) ((Date) source).getTime();
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Integer.");
	}
	

	public final String formatObject(Object value) {
		return format((Integer) value);
	}


	public static final IntegerAdapter ENGLISH = new IntegerAdapter(Locale.ENGLISH);

}
