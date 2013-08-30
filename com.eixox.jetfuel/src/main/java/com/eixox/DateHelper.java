package com.eixox;

import java.util.Date;

public final class DateHelper {

	public static final long getTotalMilliseconds(Date start, Date end) {
		Date dt = new Date();
		dt.setTime(end.getTime() - start.getTime());
		return dt.getTime();
	}

	public static final double getTotalSeconds(Date start, Date end) {
		return getTotalMilliseconds(start, end) / 1000.0;
	}

	public static final double getTotalMinutes(Date start, Date end) {
		return getTotalSeconds(start, end) / 60.0;
	}

	public static final double getTotalHours(Date start, Date end) {
		return getTotalSeconds(start, end) / 3600.0;
	}

	public static final Date addDays(Date start, int days) {

		return addHours(start, days * 24);
	}

	public static final Date addHours(Date start, int hours) {

		long timeToAdd = hours * 60 * 60 * 1000;
		return new Date(start.getTime() + timeToAdd);
	}
}
