package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class BooleanAdapter implements ValueAdapter<Boolean> {

	public final Boolean convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Boolean)
			return (Boolean) value;
		else if (Boolean.TYPE.isInstance(value))
			return (Boolean) value;
		else if (value instanceof String)
			return parse((String) value);
		else if (value instanceof Number)
			return ((Number) value).intValue() == 1;
		else
			throw new RuntimeException("Can't convert " + value + " to Boolean");

	}

	public final Boolean parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Boolean.parseBoolean(text);
	}

	public final Boolean parse(String text, Locale locale) {
		return parse(text);
	}

	public final String format(Object value) {
		if (value == null)
			return "";
		else
			return value.toString();
	}

	public final String format(Object value, Locale locale) {
		return format(value);
	}

	public final String formatSql(Object value) {
		if (value == null)
			return "NULL";
		else
			return ((Boolean)value) ? "1" : "0";
	}

	public final Boolean readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getBoolean(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
