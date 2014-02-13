package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class LongAdapter implements ValueAdapter<Long> {

	public final Long convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Long)
			return (Long) value;
		else if (Long.TYPE.isInstance(value))
			return (Long) value;
		else if (value instanceof Number)
			return ((Number) value).longValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Long");
	}

	public final Long parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Long.parseLong(text);
	}

	public final Long parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).longValue();
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

	public final Long readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getLong(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
