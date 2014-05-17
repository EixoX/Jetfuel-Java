package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class IntegerAdapter extends ValueAdapter<Integer> {

	public IntegerAdapter() {
		super(Integer.class);
	}

	@Override
	public final Integer parse(Culture culture, String input) {
		Number n = culture.parseNumber(input);
		return n == null ? 0 : n.intValue();
	}

	@Override
	public final String format(Culture culture, Integer input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0");
		else
			builder.append((Integer) input);
	}

	@Override
	public final Integer readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getInt(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Integer) item) == 0;
	}

	@Override
	public final Integer convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (Integer.class.isInstance(value) || Integer.TYPE.isInstance(value))
			return (Integer) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).intValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0;
	}
}
