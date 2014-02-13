package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class CharacterAdapter implements ValueAdapter<Character> {

	public final Character convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Character)
			return (Character) value;
		else if (Character.TYPE.isInstance(value))
			return (Character) value;
		else if (value instanceof Number)
			return (char) ((Number) value).intValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Character");
	}

	public final Character parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return text.charAt(0);
	}

	public final Character parse(String text, Locale locale) {
		return parse(text);
	}

	public final String format(Object value) {
		return value == null ? "" : value.toString();
	}

	public final String format(Object value, Locale locale) {
		return value == null ? "" : value.toString();
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : "'" + value.toString().replaceAll("'", "''") + "'";
	}

	public final Character readFrom(ResultSet rs, int ordinal) {
		try {
			return parse(rs.getString(ordinal));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
