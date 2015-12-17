package com.eixox.adapters;

import java.sql.PreparedStatement;
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
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Integer) item) == 0;
	}

	@Override
	public final Integer convert(Object value, Culture culture) {
		if (value == null)
			return 0;
		else if (Integer.class.isInstance(value) || Integer.TYPE.isInstance(value))
			return (Integer) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).intValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.INTEGER;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Integer value) throws SQLException {
		ps.setInt(parameterIndex, value);
	}

	@Override
	public Integer readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getInt(ordinal);
	}

	public static final int parseInt(String input) {
		if (input == null || input.isEmpty())
			return 0;
		else
			return Integer.parseInt(input);
	}
}
