package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class StringAdapter extends ValueAdapter<String> {

	public StringAdapter() {
		super(String.class);
	}

	@Override
	public final String parse(Culture culture, String input) {
		return input;
	}

	@Override
	public final String format(Culture culture, String input) {
		return input;
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "''");
		else
		{
			builder.append("'");
			builder.append(((String) input).replace("'", "''"));
			builder.append("'");
		}
	}

	@Override
	public final String readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getString(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((String) item).isEmpty();
	}

	@Override
	public final String convert(Object value) {
		if (value == null)
			return null;
		else if (String.class.isInstance(value))
			return (String) value;
		else
			return value.toString();
	}

}
