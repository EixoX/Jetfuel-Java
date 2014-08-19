package com.eixox.adapters;

import java.sql.PreparedStatement;
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
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Number) item).intValue() == 0;
	}

	@Override
	public final Number convert(Object value, Culture culture) {
		if (value == null)
			return 0.0;
		else if (Number.class.isInstance(value))
			return ((Number) value);
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
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Number value) throws SQLException {
		ps.setDouble(parameterIndex, value == null ? null : value.doubleValue());
	}

	@Override
	public Number readValue(ResultSet rs, int ordinal) throws SQLException {
		return (Number) rs.getObject(ordinal);
	}
}
