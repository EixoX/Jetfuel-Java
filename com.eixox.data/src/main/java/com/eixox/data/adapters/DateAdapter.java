package com.eixox.data.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.eixox.data.ValueAdapter;

public final class DateAdapter implements ValueAdapter<Date> {

	public static final DateFormat rfc2822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
	public static final DateFormat Default_SimpleDate = SimpleDateFormat.getInstance();
	public static final DateFormat Universal_Date = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat Universal_DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
	public static final DateFormat PtBr_Date = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat PtBr_DateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final DateFormat Default_Java = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
	public static final SimpleDateFormat SqlDateFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss.S''");

	public static final DateFormat[] UniversalFormats = new DateFormat[] {
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S") };

	public static final DateFormat[] EnglishFormats = new DateFormat[] {
			new SimpleDateFormat("MM/dd/yyyy"),
			new SimpleDateFormat("MM/dd/yyyy HH:mm"),
			new SimpleDateFormat("MM/dd/yyyy HH:mm a"),
			new SimpleDateFormat("MM/dd/yyyy HH:mm:ss"),
			new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.S"),
			new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.S a"),
			new SimpleDateFormat("MMM dd, yyyy"),
			new SimpleDateFormat("MMMM dd, yyyy"),
			new SimpleDateFormat("EEE MMM dd, yyyy"),
			new SimpleDateFormat("EEE MMMM dd, yyyy"),
			new SimpleDateFormat("EEEE MMM dd, yyyy"),
			new SimpleDateFormat("EEEE MMMM dd, yyyy") };

	public static final DateFormat[] NonEnglishFormats = new DateFormat[] {
			new SimpleDateFormat("dd/MM/yyyy"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm a"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S"),
			new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.S a"),
			new SimpleDateFormat("dd MMM yyyy"),
			new SimpleDateFormat("EEE dd MMM yyyy") };

	public final Date convert(Object value) {
		if (value == null)
			return null;
		else if (value instanceof Date)
			return ((Date) value);
		else if (value instanceof Calendar)
			return ((Calendar) value).getTime();
		else if (value instanceof String)
			return parse((String) value);
		else
			throw new RuntimeException("Unable to convert " + value + " to Date");
	}

	public final Date parse(String text) {
		return parse(text, Locale.ENGLISH);
	}

	public final Date parse(String text, Locale locale) {
		if (text == null || text.isEmpty())
			return null;
		else {
			int length = text.length();
			try {
				if (Character.isDigit(text.charAt(0))) {
					if (text.indexOf('-') > 0) {
						if (length < 12)
							return Universal_Date.parse(text);
						else
							return Universal_DateTime.parse(text);
					} else {
						if (length < 11)
							return DateFormat.getDateInstance(DateFormat.SHORT, locale).parse(text);
						else if (length < 18)
							return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).parse(text);
						else
							return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale).parse(text);
					}
				} else if (length < 13) {
					return DateFormat.getDateInstance(DateFormat.MEDIUM, locale).parse(text);
				} else if (length < 20) {
					return DateFormat.getDateInstance(DateFormat.LONG, locale).parse(text);
				} else if (text.indexOf(',') > 0)
					return rfc2822DateFormat.parse(text);
				else
					return Default_Java.parse(text);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public final String format(Object value) {
		return value == null ? "" : Universal_DateTime.format(value);
	}

	public final String format(Object value, Locale locale) {
		return value == null ? "" : DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale).format(value);
	}

	public final String formatSql(Object value) {
		return value == null ? "NULL" : SqlDateFormat.format(value);
	}

	public final Date readFrom(ResultSet rs, int ordinal) {
		try {
			return rs.getDate(ordinal);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
