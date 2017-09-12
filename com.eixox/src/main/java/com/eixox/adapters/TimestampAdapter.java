package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.util.Date;

import com.eixox.globalization.Culture;

public class TimestampAdapter extends ValueAdapter<Timestamp> {

	public TimestampAdapter() {
		super(Timestamp.class);
	}

	@Override
	public final Timestamp parse(Culture culture, String input) {
		try {

			if (input == null || input.isEmpty() || "null".equalsIgnoreCase(input))
				return null;
			else if (input.indexOf('-') < 0)
				return new Timestamp(Long.parseLong(input));
			else if(input.indexOf('T') > 0)
				return Timestamp.valueOf(input.replace('T', ' ').substring(0,  19));
			else
				return Timestamp.valueOf(input);
		} catch (Exception e) {
			throw new RuntimeException(input, e);
		}

	}

	@Override
	public final int getSqlTypeId() {
		return Types.TIMESTAMP;
	}

	@Override
	public final void setParameterValue(PreparedStatement ps, int parameterIndex, Timestamp value) throws SQLException {
		ps.setTimestamp(parameterIndex, value);
	}

	@Override
	public final Timestamp readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getTimestamp(ordinal);
	}

	@Override
	public final String format(Culture culture, Timestamp input) {
		return input.toString();
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Timestamp) item).getTime() == 0L;
	}

	@Override
	public final Timestamp convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (value instanceof Timestamp)
			return (Timestamp) value;
		else if (value instanceof Date)
			return new Timestamp(((Date) value).getTime());
		else if (value instanceof java.sql.Date)
			return new Timestamp(((java.sql.Date) value).getTime());
		else if (value instanceof Instant)
			return Timestamp.from((Instant) value);
		else if (value instanceof String)
			return parse((String) value);
		else if (value instanceof Number)
			return new Timestamp(((Number) value).longValue());
		else
			throw new RuntimeException("Can't convert " + value.getClass() + " to Timestamp");
	}

}
