package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class StringAdapter extends ValueAdapter<String> {

	public StringAdapter() {
		super(String.class);
	}

	@Override
	public final String parse(Culture culture, String input) {
		return input;
	}

	@Override
	public final String format(Culture culture, String input) {
		return input;
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((String) item).isEmpty();
	}

	@Override
	public final String convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (String.class.isInstance(value))
			return (String) value;
		else
			return value.toString();
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.VARCHAR;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, String value) throws SQLException {
		ps.setString(parameterIndex, value);
	}

	@Override
	public String readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getString(ordinal);
	}

}
