package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class BooleanAdapter extends ValueAdapter<Boolean> {

	public BooleanAdapter() {
		super(Boolean.class);
	}

	@Override
	public final Boolean parse(Culture culture, String input) {
		if (input == null || input.isEmpty())
			return null;
		else if (input.equalsIgnoreCase("on"))
			return true;
		else
			return Boolean.parseBoolean(input);
	}

	@Override
	public final String format(Culture culture, Boolean input) {
		if (input == null)
			return "";
		else
			return Boolean.toString(input);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null;
	}

	@Override
	public final Boolean convert(Object value, Culture culture) {
		if (value == null)
			return false;
		else if (Boolean.class.isInstance(value) || Boolean.TYPE.isInstance(value))
			return (Boolean) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).intValue() > 0;
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return false;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.BOOLEAN;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Boolean value) throws SQLException {
		ps.setBoolean(parameterIndex, value);
	}

	@Override
	public Boolean readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getBoolean(ordinal);
	}

}
