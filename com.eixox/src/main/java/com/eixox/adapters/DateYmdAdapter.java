package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateYmdAdapter implements ValueAdapter<Date> {

	public static final DateYmdAdapter INSTANCE = new DateYmdAdapter();

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

	public static final Date parseDate(String input) {
		Calendar cal = parseCalendar(input);
		return cal == null ? null : cal.getTime();
	}

	public static final Timestamp parseTimestamp(String input) {
		Calendar cal = parseCalendar(input);
		return cal == null ? null : new Timestamp(cal.getTimeInMillis());
	}

	public final Class<Date> getDataType() {
		return Date.class;
	}

	public final String format(Date value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(value.getTime());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return StringAdapter.concat(
				StringAdapter.right("0000" + year, 4),
				StringAdapter.right("0000" + month, 2),
				StringAdapter.right("0000" + day, 2),
				StringAdapter.right("0000" + hour, 2),
				StringAdapter.right("0000" + minute, 2),
				StringAdapter.right("0000" + second, 2));
	}

	public final Date parse(String input) {
		return parseDate(input);
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			Date dt = parse(source);
			field.set(target, dt);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Timestamp ts = source.getTimestamp(position);
			field.set(target, ts == null ? null : new Date(ts.getTime()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Timestamp ts = source.getTimestamp(name);
			field.set(target, ts == null ? null : new Date(ts.getTime()));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Date dt = (Date) field.get(source);
			target.setTimestamp(position, dt == null ? null : new Timestamp(dt.getTime()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void formatSql(Date source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append("'");
			target.append(new Timestamp(source.getTime()));
			target.append("'");
		}
	}

	public final Date convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Date)
			return (Date) source;
		else if (source instanceof java.sql.Date)
			return (Date) source;
		else if (source instanceof Timestamp)
			return (Date) source;
		else if (source instanceof String)
			return parse((String) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Date.");
	}
	

	public final String formatObject(Object value) {
		return format((Date) value);
	}


}
