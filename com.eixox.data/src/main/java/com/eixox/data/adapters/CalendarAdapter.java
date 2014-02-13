package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.eixox.data.ValueAdapter;
import com.eixox.data.ValueAdapters;

public final class CalendarAdapter implements ValueAdapter<Calendar> {

	public final Calendar convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Calendar)
			return ((Calendar) value);
		else if (value instanceof Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) value);
			return cal;
		} else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Can't convert " + value + " to Calendar");
	}

	public final Calendar parse(String text) {
		Date dt = ValueAdapters.DateAdapter.parse(text);
		if (dt == null)
			return null;
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return cal;
		}
	}

	public final Calendar parse(String text, Locale locale) {
		Date dt = ValueAdapters.DateAdapter.parse(text, locale);
		if (dt == null)
			return null;
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return cal;
		}
	}

	public final String format(Object value) {
		return value == null ? "" : value.toString();
	}

	public final String format(Object value, Locale locale) {
		return value == null ? "" : value.toString();
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : ValueAdapters.DateAdapter.formatSql(((Calendar) value).getTime());
	}

	public Calendar readFrom(ResultSet rs, int ordinal) {
		Date dt;
		try {
			dt = rs.getDate(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		if (dt == null)
			return null;
		else {
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			return cal;
		}
	}

}
