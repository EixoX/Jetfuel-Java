package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.eixox.globalization.Culture;

public class TimestampYmdAdapter extends ValueAdapter<Timestamp> {

	public TimestampYmdAdapter() {
		super(Timestamp.class);
	}

	@Override
	public Timestamp parse(Culture culture, String input) {
		return DateYmdAdapter.parseTimestamp(input);
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.TIMESTAMP;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Timestamp value) throws SQLException {
		ps.setTimestamp(parameterIndex, value);
	}

	@Override
	public Timestamp readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getTimestamp(ordinal);
	}

	@Override
	public String format(Culture culture, Timestamp input) {
		return DateYmdAdapter.INSTANCE.format(input);
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null || ((Timestamp) item).getTime() == 0L;
	}

	@Override
	public Timestamp convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (value instanceof Timestamp)
			return ((Timestamp) value);
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value.getClass() + " to Timestamp.");
	}

}
