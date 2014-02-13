package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class ShortAdapter implements ValueAdapter<Short> {

	public final Short convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Short)
			return (Short) value;
		else if (Short.TYPE.isInstance(value))
			return (Short) value;
		else if (value instanceof Number)
			return ((Number) value).shortValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Short");
	}

	public final Short parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Short.parseShort(text);
	}

	public final Short parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).shortValue();
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

	public final Short readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getShort(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
