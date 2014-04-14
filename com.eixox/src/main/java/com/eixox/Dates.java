package com.eixox;

import java.util.Calendar;
import java.util.Date;

public final class Dates {
	private static final int[] daysInMonthArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	public static final boolean isLeapYear(int year) {
		if (year % 400 == 0)
			return true;
		else if (year % 100 == 0)
			return false;
		else
			return (year % 4 == 0);
	}

	public static final int daysInMonth(int year, int month) {
		if (month < 1 || month > 12)
			throw new RuntimeException("Months are in range [1, 12]");
		else
			return month == 2 ? (isLeapYear(year) ? 29 : 28) : daysInMonthArray[month];
	}

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

	public static final int yearsBetween(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(d1);

		Calendar c2 = Calendar.getInstance();
		c2.setTime(d2);

		return yearsBetween(c1, c2);
	}
}
