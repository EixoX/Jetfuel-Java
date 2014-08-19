package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class FloatAdapter extends ValueAdapter<Float> {

	public FloatAdapter() {
		super(Float.class);
	}

	@Override
	public final Float parse(Culture culture, String input) {
		Number n = culture.parseNumber(input);
		return n == null ? 0F : n.floatValue();
	}

	@Override
	public final String format(Culture culture, Float input) {
		return culture.formatNumber(input);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Float) item) == 0F;
	}

	@Override
	public final Float convert(Object value, Culture culture) {
		if (value == null)
			return 0F;
		else if (Float.class.isInstance(value) || Float.TYPE.isInstance(value))
			return (Float) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).floatValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0.0F;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.FLOAT;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Float value) throws SQLException {
		ps.setFloat(parameterIndex, value);
	}

	@Override
	public Float readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getFloat(ordinal);
	}

}
