package com.eixox.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class DateHelper {

	public static final DateFormat DefaultDateFormat = DateFormat.getDateInstance();
	public static final DateFormat DefaultDateTimeFormat = DateFormat.getDateTimeInstance();
	public static final DateFormat DefaultTimeFormat = DateFormat.getTimeInstance();

	public static final int[] DaysInMonth = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final boolean isLeapYear(int year) {
		return ((year % 400 == 0) || (year % 4 == 0 && year % 100 != 0));
	}

	public static final int daysInMonth(int year, int month) {
		return (month == 2 && isLeapYear(year)) ? 28 : DaysInMonth[month];
	}

	public static final Date parseDate(String input) throws ParseException {
		return DefaultDateFormat.parse(input);
	}

	public static final Date parseDateTime(String input) throws ParseException {
		return DefaultDateTimeFormat.parse(input);
	}

	public static final Date parseTime(String input) throws ParseException {
		return DefaultTimeFormat.parse(input);
	}

}
