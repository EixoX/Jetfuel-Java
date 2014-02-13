package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

@SuppressWarnings("rawtypes")
public class EnumAdapter<T extends Enum> implements ValueAdapter<T> {

	private final Class<T> claz;

	public EnumAdapter(Class<T> claz) {
		this.claz = claz;
	}

	@SuppressWarnings("unchecked")
	public final T convert(Object value) {
		if (value == null)
			return null;
		else if (this.claz.isInstance(value))
			return (T) value;
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to " + claz);

	}

	public final T parse(String text, Locale locale) {
		return parse(text);
	}

	@SuppressWarnings("unchecked")
	public final T parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return (T) Enum.valueOf(claz, text);
	}

	public final String format(Object value, Locale locale) {
		return format(value);
	}

	public final String format(Object value) {
		return value == null ? "" : ((Enum) value).name();
	}

	public final String formatSql(Object value) {
		if (value == null)
			return "NULL";
		else
			return "'" + ((Enum) value).name() + "'";
	}

	public final T readFrom(ResultSet rs, int ordinal) {
		try {
			return parse(rs.getString(ordinal));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
