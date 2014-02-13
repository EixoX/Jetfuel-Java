package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class DoubleAdapter implements ValueAdapter<Double> {

	public final Double convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Double)
			return (Double) value;
		else if (Double.TYPE.isInstance(value))
			return (Double) value;
		else if (value instanceof Number)
			return ((Number) value).doubleValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Double");
	}

	public final Double parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Double.parseDouble(text);
	}

	public final Double parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).doubleValue();
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

	public final Double readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getDouble(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
