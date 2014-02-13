package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class FloatAdapter implements ValueAdapter<Float> {

	public final Float convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Float)
			return (Float) value;
		else if (Float.TYPE.isInstance(value))
			return (Float) value;
		else if (value instanceof Number)
			return ((Number) value).floatValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Float");
	}

	public final Float parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Float.parseFloat(text);
	}

	public final Float parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).floatValue();
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
	}

	public final String format(Object value) {
		return value == null ? "" : value.toString();
	}

	public final String format(Object value, Locale locale) {
		return value == null ? "" : DecimalFormat.getInstance(locale).format(value);
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : value.toString();
	}

	public final Float readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getFloat(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
