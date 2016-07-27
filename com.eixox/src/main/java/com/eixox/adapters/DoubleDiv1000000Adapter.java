package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public class DoubleDiv1000000Adapter extends ValueAdapter<Double> {

	public DoubleDiv1000000Adapter() {
		super(Double.class);
	}

	@Override
	public Double parse(Culture culture, String input) {
		return input == null || input.isEmpty() ? 0.0 : culture.parseNumber(input).doubleValue() / 1000000.0;
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

	@Override
	public String format(Culture culture, Double input) {
		return Long.toString((long) Math.round(input * 1000000.0));
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null || ((Number) item).doubleValue() == 0.0;
	}

	@Override
	public Double convert(Object value, Culture culture) {
		if (value == null)
			return 0.0;
		else if (value instanceof Number)
			return ((Number) value).doubleValue();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value.getClass() + " to double.");
	}

}
