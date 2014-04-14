package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class BooleanAdapter extends ValueAdapter<Boolean> {

	public BooleanAdapter() {
		super(Boolean.class);
	}

	@Override
	public final Boolean parse(Culture culture, String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return Boolean.parseBoolean(input);
	}

	@Override
	public final String format(Culture culture, Boolean input) {
		if (input == null)
			return "";
		else
			return Boolean.toString(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0");
		else
			builder.append(((Boolean) input) ? "1" : "0");
	}

	@Override
	public final Boolean readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getBoolean(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null;
	}

	@Override
	public final Boolean convert(Object value) {
		if (value == null)
			return null;
		else if (Boolean.class.isInstance(value) || Boolean.TYPE.isInstance(value))
			return (Boolean) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).intValue() > 0;
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return false;
	}

}
