package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public class NumberAdapter extends ValueAdapter<Number> {

	public NumberAdapter() {
		super(Number.class);
	}

	@Override
	public final Number parse(Culture culture, String input) {
		return culture.parseNumber(input);
	}

	@Override
	public final String format(Culture culture, Number input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0.0");
		else
			builder.append(((Number) input).doubleValue());

	}

	@Override
	public final Number readSql(ResultSet rs, int ordinal) throws SQLException {
		return (Number) rs.getObject(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Number) item).intValue() == 0;
	}

	@Override
	public final Number convert(Object value) {
		if (value == null)
			return null;
		else if (Number.class.isInstance(value))
			return ((Number) value);
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0.0;
	}
}
