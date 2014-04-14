package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class LongAdapter extends ValueAdapter<Long> {

	public LongAdapter() {
		super(Long.class);
	}

	@Override
	public final Long parse(Culture culture, String input) {
		Number n = culture.parseNumber(input).longValue();
		return n == null ? null : n.longValue();
	}

	@Override
	public final String format(Culture culture, Long input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0");
		else
			builder.append((Long) input);

	}

	@Override
	public final Long readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getLong(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Long) item) == 0L;
	}

	@Override
	public final Long convert(Object value) {
		if (value == null)
			return null;
		else if (Long.class.isInstance(value) || Long.TYPE.isInstance(value))
			return (Long) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).longValue();
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0L;
	}
}
