package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class ShortAdapter extends ValueAdapter<Short> {

	public ShortAdapter() {
		super(Short.class);
	}

	@Override
	public final Short parse(Culture culture, String input) {
		Number n = culture.parseNumber(input).shortValue();
		return n == null ? null : n.shortValue();
	}

	@Override
	public final String format(Culture culture, Short input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0");
		else
			builder.append((Short) input);
	}

	@Override
	public final Short readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getShort(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Short) item) == 0;
	}

	@Override
	public final Short convert(Object value) {
		if (value == null)
			return null;
		else if (Short.class.isInstance(value) || Short.TYPE.isInstance(value))
			return (Short) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).shortValue();
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0;
	}
}
