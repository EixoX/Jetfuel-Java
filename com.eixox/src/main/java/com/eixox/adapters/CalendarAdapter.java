package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import com.eixox.globalization.Culture;

public final class CalendarAdapter extends ValueAdapter<Calendar> {

	public CalendarAdapter() {
		super(Calendar.class);
	}

	@Override
	public final Calendar parse(Culture culture, String input) {
		Date dt = culture.parseDateTime(input);
		if (dt == null)
			return null;
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return cal;
		}
	}

	@Override
	public final String format(Culture culture, Calendar input) {
		return input == null ? "" : culture.formatDateTime(input.getTime());
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append("NULL");
		else
			ValueAdapters.DATE_TIME.appendSql(builder, ((Calendar) input).getTime(), nullable);
	}

	@Override
	public final Calendar readSql(ResultSet rs, int ordinal) throws SQLException {
		java.sql.Date date = rs.getDate(ordinal);
		if (date == null)
			return null;
		else
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		}
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ZERO.equals(item);
	}

	@Override
	public final Calendar convert(Object value) {
		if (value == null)
			return null;
		else if (Calendar.class.isInstance(value))
			return (Calendar) value;
		else if (Date.class.isInstance(value))
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) value);
			return cal;
		}
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return ZERO;
	}

	public static final Calendar	ZERO;
	static {
		ZERO = Calendar.getInstance();
		ZERO.setTimeInMillis(0);
	}

}
