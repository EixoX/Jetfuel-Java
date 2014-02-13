package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class StringAdapter implements ValueAdapter<String> {

	public final String convert(Object value) {
		return value == null ? null : value.toString();
	}

	public final String parse(String text) {
		return text;
	}

	public final String parse(String text, Locale locale) {
		return text;
	}

	public final String format(Object value) {
		return value == null ? "" : (String)value;
	}

	public final String format(Object value, Locale locale) {
		return value == null ? "" : (String)value;
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : ("'" + ((String)value).replaceAll("'", "''") + "'");
	}

	public final String readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getString(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
