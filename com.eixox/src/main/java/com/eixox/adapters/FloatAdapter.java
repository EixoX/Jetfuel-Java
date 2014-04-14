package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class FloatAdapter extends ValueAdapter<Float> {

	public FloatAdapter() {
		super(Float.class);
	}

	@Override
	public final Float parse(Culture culture, String input) {
		Number n = culture.parseNumber(input).floatValue();
		return n == null ? null : n.floatValue();
	}

	@Override
	public final String format(Culture culture, Float input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0.0");
		else
			builder.append((Float) input);
	}

	@Override
	public final Float readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getFloat(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Float) item) == 0F;
	}

	@Override
	public final Float convert(Object value) {
		if (value == null)
			return null;
		else if (Float.class.isInstance(value) || Float.TYPE.isInstance(value))
			return (Float) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).floatValue();
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0.0F;
	}

}
