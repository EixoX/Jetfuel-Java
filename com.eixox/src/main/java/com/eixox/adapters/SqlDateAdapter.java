package com.eixox.adapters;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.eixox.globalization.Culture;

public class SqlDateAdapter extends ValueAdapter<java.sql.Date> {

	public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	protected SqlDateAdapter() {
		super(Date.class);
	}

	@Override
	public Date parse(Culture culture, String input) {
		if (input == null || input.isEmpty())
			return null;
		try {
			java.util.Date dt = DATE_FORMAT.parse(input);
			return new Date(dt.getTime());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getSqlTypeId() {
		return Types.DATE;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Date value) throws SQLException {
		ps.setDate(parameterIndex, value);
	}

	@Override
	public Date readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDate(ordinal);
	}

	@Override
	public String format(Culture culture, Date input) {
		return input.toString();
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null || ((Date) item).getTime() == 0L;
	}

	@Override
	public Date convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (value instanceof Date)
			return (Date) value;
		else if (value instanceof Timestamp)
			return new Date(((Timestamp) value).getTime());
		else if (value instanceof java.util.Date)
			return new Date(((java.util.Date) value).getTime());
		else
			return parse(value.toString());
	}
}
