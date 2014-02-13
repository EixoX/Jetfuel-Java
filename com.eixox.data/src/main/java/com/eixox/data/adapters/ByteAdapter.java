package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class ByteAdapter implements ValueAdapter<Byte> {

	public final Byte convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Byte)
			return (Byte) value;
		else if (Byte.TYPE.isInstance(value))
			return (Byte) value;
		else if (value instanceof Number)
			return ((Number) value).byteValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Byte");
	}

	public final Byte parse(String text) {
		if (text == null || text.isEmpty())
			return null;
		else
			return Byte.parseByte(text);
	}

	public final Byte parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else
			try {
				return DecimalFormat.getInstance(locale).parse(text).byteValue();
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

	public final Byte readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getByte(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	

}
