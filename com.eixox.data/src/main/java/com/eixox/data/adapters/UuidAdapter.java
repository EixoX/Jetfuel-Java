package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.UUID;

import com.eixox.data.ValueAdapter;

public final class UuidAdapter implements ValueAdapter<UUID> {

	public final UUID convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof UUID)
			return (UUID) value;
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert from " + value + " to UUID");
	}

	public final UUID parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return UUID.fromString(text);
	}

	public final UUID parse(String text, Locale locale) {
		return parse(text);
	}

	public final String format(Object value) {
		return value == null ? "" : value.toString();
	}

	public final String format(Object value, Locale locale) {
		return format(value);
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : ("'" + value.toString() + "'");
	}

	public final UUID readFrom(ResultSet rs, int ordinal) {
		try {
			return parse(rs.getString(ordinal));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
