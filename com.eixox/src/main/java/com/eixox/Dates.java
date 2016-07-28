package com.eixox;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class Dates {
	private static final int[] daysInMonthArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	// ______________________________________________________________________
	public static final boolean isLeapYear(int year) {
		if (year % 400 == 0)
			return true;
		else if (year % 100 == 0)
			return false;
		else
			return (year % 4 == 0);
	}

	// ______________________________________________________________________
	public static final int daysInMonth(int year, int month) {
		if (month < 1 || month > 12)
			throw new RuntimeException("Months are in range [1, 12]");
		else
			return month == 2 ? (isLeapYear(year) ? 29 : 28) : daysInMonthArray[month];
	}

	// ______________________________________________________________________
	public static final int yearsBetween(Calendar c1, Calendar c2) {

		int y1 = c1.get(Calendar.YEAR);
		int m1 = c1.get(Calendar.MONTH);
		int d1 = c1.get(Calendar.DAY_OF_MONTH);

		int y2 = c2.get(Calendar.YEAR);
		int m2 = c2.get(Calendar.MONTH);
		int d2 = c2.get(Calendar.DAY_OF_MONTH);

		if (m1 > m2)
			return y1 - y2;
		else if (m1 < m2)
			return y1 - y2 - 1;
		else
			return d1 < d2 ? (y1 - y2 - 1) : (y1 - y2);
	}

	// ______________________________________________________________________
	public static final int yearsBetween(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		return yearsBetween(c1, c2);
	}

	// ______________________________________________________________________
	public static final long getTotalMilliseconds(Date start, Date end) {
		Date dt = new Date();
		dt.setTime(end.getTime() - start.getTime());
		return dt.getTime();
	}

	// ______________________________________________________________________
	public static final double getTotalSeconds(Date start, Date end) {
		return getTotalMilliseconds(start, end) / 1000.0;
	}

	// ______________________________________________________________________
	public static final double getTotalMinutes(Date start, Date end) {
		return getTotalSeconds(start, end) / 60.0;
	}

	// ______________________________________________________________________
	public static final double getTotalHours(Date start, Date end) {
		return getTotalSeconds(start, end) / 3600.0;
	}

	// ______________________________________________________________________
	public static final Date addDays(Date start, int days) {
		return addHours(start, days * 24);
	}

	// ______________________________________________________________________
	public static final Date addHours(Date start, int hours) {

		long timeToAdd = hours * 60 * 60 * 1000;
		return new Date(start.getTime() + timeToAdd);
	}

	// ______________________________________________________________________
	public static final Date addMinutes(Date start, int minutes) {

		long timeToAdd = minutes * 60 * 1000;
		return new Date(start.getTime() + timeToAdd);
	}

	// ______________________________________________________________________
	public static final Date addSeconds(Date start, int seconds) {

		long timeToAdd = seconds * 1000;
		return new Date(start.getTime() + timeToAdd);
	}

	// ______________________________________________________________________
	public static final Date create(int year, int month, int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	// ______________________________________________________________________
	public static final Date tomorrow() {
		Calendar greg = GregorianCalendar.getInstance();
		greg.add(Calendar.DAY_OF_MONTH, 1);
		return greg.getTime();
	}

	// ______________________________________________________________________
	public static final Date getDateWithoutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	// ______________________________________________________________________
	public static final Timestamp timestampNow() {
		return new Timestamp(new Date().getTime());
	}

	// ______________________________________________________________________
	public static final Timestamp timestampFromDate(Date date) {
		return date == null ? null : new Timestamp(date.getTime());
	}

	// ______________________________________________________________________
	public static final Time timeNow() {
		return new Time(new Date().getTime());
	}

	public static synchronized Date setTime(Date date, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, hour);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, second);
		return cal.getTime();
	}

	public static synchronized Date setTime(Date date, int hms) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, ((hms / 10000) % 100));
		cal.set(Calendar.MINUTE, ((hms / 100) % 100));
		cal.set(Calendar.SECOND, hms % 100);
		return cal.getTime();
	}
}
