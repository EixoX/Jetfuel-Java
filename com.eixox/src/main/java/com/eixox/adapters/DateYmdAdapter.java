package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.eixox.Formats;
import com.eixox.Strings;
import com.eixox.globalization.Culture;

public class DateYmdAdapter extends ValueAdapter<Date> {

	public static final DateYmdAdapter INSTANCE = new DateYmdAdapter();

	public DateYmdAdapter() {
		super(Date.class);
	}

	public static final GregorianCalendar parseCalendar(String input) {

		if (input == null || input.isEmpty())
			return null;

		int year = 0;
		int month = 0;
		int day = 0;
		int hour = 0;
		int minute = 0;
		int second = 0;

		switch (input.length()) {
		case 1:
		case 2:
		case 3:
			year = Integer.parseInt(input);
			month = 1;
			day = 1;
			break;
		case 4:
			year = Integer.parseInt(input.substring(0, 2));
			month = Integer.parseInt(input.substring(2, 4));
			day = 1;
			break;
		case 5:
			year = Integer.parseInt(input.substring(0, 2));
			month = Integer.parseInt(input.substring(2, 3));
			day = Integer.parseInt(input.substring(3, 5));
			break;
		case 6:
			year = Integer.parseInt(input.substring(0, 2));
			month = Integer.parseInt(input.substring(2, 4));
			day = Integer.parseInt(input.substring(4, 6));
			break;
		case 7:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 5));
			day = Integer.parseInt(input.substring(5, 7));
			break;
		case 8:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			break;
		case 10:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(8, 10));
			break;
		case 12:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(8, 10));
			minute = Integer.parseInt(input.substring(10, 12));
			break;
		case 14:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(8, 10));
			minute = Integer.parseInt(input.substring(10, 12));
			second = Integer.parseInt(input.substring(12, 14));
			break;
		case 15:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(9, 11));
			minute = Integer.parseInt(input.substring(11, 13));
			second = Integer.parseInt(input.substring(13, 15));
			break;
		case 16:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(8, 10));
			minute = Integer.parseInt(input.substring(11, 13));
			second = Integer.parseInt(input.substring(14, 16));
			break;
		case 17:
			year = Integer.parseInt(input.substring(0, 4));
			month = Integer.parseInt(input.substring(4, 6));
			day = Integer.parseInt(input.substring(6, 8));
			hour = Integer.parseInt(input.substring(9, 11));
			minute = Integer.parseInt(input.substring(12, 14));
			second = Integer.parseInt(input.substring(15, 17));
			break;
		default:
			throw new RuntimeException("Unrecognizable YMD date format on " + input);
		}

		if (year < 100) {
			if (year < 60)
				year += 2000;
			else
				year += 1900;
		}

		return new GregorianCalendar(year, month - 1, day, hour, minute, second);
	}

	@Override
	public Date parse(Culture culture, String input) {
		Calendar cal = parseCalendar(input);
		return cal == null ? null : cal.getTime();
	}

	@Override
	public int getSqlTypeId() {
		return Types.TIMESTAMP;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Date value) throws SQLException {
		ps.setDate(parameterIndex, new java.sql.Date(value.getTime()));
	}

	@Override
	public Date readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDate(ordinal);
	}

	@Override
	public String format(Culture culture, Date input) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(input.getTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return Strings.concat(
				Formats.zeroPadLeft(year, 4),
				Formats.zeroPadLeft(month, 2),
				Formats.zeroPadLeft(day, 2),
				Formats.zeroPadLeft(hour, 2),
				Formats.zeroPadLeft(minute, 2),
				Formats.zeroPadLeft(second, 2));
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null || ((Date) item).getTime() == 0L;
	}

	@Override
	public Date convert(Object value, Culture culture) {
		if (value == null)
			return null;

		Class<?> claz = value.getClass();

		if (Date.class.isAssignableFrom(claz))
			return (Date) value;

		else if (Number.class.isAssignableFrom(claz))
			return new Date(((Number) value).longValue());

		else
			return parse(value.toString());

	}

	public static final Date parseDate(String input) {
		Calendar cal = parseCalendar(input);
		return cal == null ? null : cal.getTime();
	}

	public static final Timestamp parseTimestamp(String input) {
		Calendar cal = parseCalendar(input);
		return cal == null ? null : new Timestamp(cal.getTimeInMillis());
	}

}
