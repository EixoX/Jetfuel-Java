package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class IntegerAdapter implements ValueAdapter<Integer> {

	public final Integer convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Integer)
			return (Integer) value;
		else if (Integer.TYPE.isInstance(value))
			return (Integer) value;
		else if (value instanceof Number)
			return ((Number) value).intValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Integer");
	}

	public final Integer parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Integer.parseInt(text);
	}

	public final Integer parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).intValue();
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

	public final Integer readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getInt(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
