package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class DoubleAdapter extends ValueAdapter<Double> {

	public DoubleAdapter() {
		super(Double.class);
	}

	@Override
	public final Double parse(Culture culture, String input) {
		Number n = culture.parseNumber(input);
		return n == null ? 0.0 : n.doubleValue();
	}

	@Override
	public final String format(Culture culture, Double input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0.0");
		else
			builder.append((Double) input);
	}

	@Override
	public final Double readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDouble(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Double) item) == 0.0;
	}

	@Override
	public final Double convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (Double.class.isInstance(value) || Double.TYPE.isInstance(value))
			return (Double) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).doubleValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0.0;
	}

}
