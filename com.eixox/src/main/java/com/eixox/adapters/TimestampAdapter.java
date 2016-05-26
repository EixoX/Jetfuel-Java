package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

import com.eixox.globalization.Culture;

public class TimestampAdapter extends ValueAdapter<Timestamp> {

	protected TimestampAdapter() {
		super(Timestamp.class);
	}

	@Override
	public Timestamp parse(Culture culture, String input) {
		Date dt = culture.parseDate(input);
		return dt == null ? null : new Timestamp(dt.getTime());
	}

	@Override
	public int getSqlTypeId() {
		return Types.TIMESTAMP;
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
		return input.toString();
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null;
	}

	@Override
	public Timestamp convert(Object value, Culture culture) {
		return (Timestamp) value;
	}

}
