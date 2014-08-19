package com.eixox.adapters;

import java.sql.PreparedStatement;
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
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Double) item) == 0.0;
	}

	@Override
	public final Double convert(Object value, Culture culture) {
		if (value == null)
			return 0.0;
		else if (Double.class.isInstance(value) || Double.TYPE.isInstance(value))
			return (Double) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).doubleValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0.0;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.DOUBLE;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Double value) throws SQLException {
		ps.setDouble(parameterIndex, value);
	}

	@Override
	public Double readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDouble(ordinal);
	}

}
