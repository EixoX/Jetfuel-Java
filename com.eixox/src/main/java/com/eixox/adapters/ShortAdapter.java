package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public final class ShortAdapter implements ValueAdapter<Short> {

	public final NumberFormat numberFormat;

	public ShortAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public ShortAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public ShortAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Short> getDataType() {
		return Short.TYPE;
	}

	public final String format(Short input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Short parse(String input) {
		return parseShort(input);
	}

	public final short parseShort(String input) {
		if (input == null || input.isEmpty())
			return 0;
		else
			try {
				return this.numberFormat.parse(input).shortValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			short d = parseShort(source);
			field.setShort(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			short d = source.getShort(position);
			field.setShort(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			short d = source.getShort(name);
			field.setShort(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			short d = field.getShort(source);
			target.setShort(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Short source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}
	public final Short convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Short)
			return (Short) source;
		else if (source instanceof Number)
			return ((Number) source).shortValue();
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Date)
			return (short)((Date) source).getTime();
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Short.");
	}
	

	public final String formatObject(Object value) {
		return format((Short) value);
	}

	
	public static final ShortAdapter ENGLISH = new ShortAdapter(Locale.ENGLISH);

}
