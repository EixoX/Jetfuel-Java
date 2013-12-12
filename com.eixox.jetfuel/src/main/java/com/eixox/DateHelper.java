package com.eixox;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class DateHelper {

	public static final DateFormat rfc2822DateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
	public static final DateFormat Default_SimpleDate = SimpleDateFormat.getInstance();
	public static final DateFormat Universal_Date = new SimpleDateFormat("yyyy-MM-dd");
	public static final DateFormat Universal_DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final DateFormat PtBr_Date = new SimpleDateFormat("dd/MM/yyyy");
	public static final DateFormat PtBr_DateTime = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final DateFormat Default_Java = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");

	//______________________________________________________________________
	public static final DateFormat guessDateFormat(String input, Locale locale) {
		if (input == null || input.isEmpty())
			return DateFormat.getInstance();

		int length = input.length();

		if (Character.isDigit(input.charAt(0))) {
			if (input.indexOf('-') > 0) {
				if (length < 12)
					return Universal_Date;
				else
					return Universal_DateTime;
			} else {
				if (length < 11)
					return DateFormat.getDateInstance(DateFormat.SHORT, locale);
				else if (length < 18)
					return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
				else
					return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.LONG, locale);
			}
		} else if (length < 13) {
			return DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
		} else if (length < 20) {
			return DateFormat.getDateInstance(DateFormat.LONG, locale);
		} else if (input.indexOf(',') > 0)
			return rfc2822DateFormat;
		else
			return Default_Java;
	}

	//______________________________________________________________________
	public static final Date parse(String input, Locale locale) {
		try {
			return guessDateFormat(input, locale).parse(input);
		} catch (ParseException e) {
			return null;
		}
	}
	

	//______________________________________________________________________
	public static final Date parse(String input) {
		return parse(input, Locale.ENGLISH);
	}

	//______________________________________________________________________
	public static final long getTotalMilliseconds(Date start, Date end) {
		Date dt = new Date();
		dt.setTime(end.getTime() - start.getTime());
		return dt.getTime();
	}

	//______________________________________________________________________
	public static final double getTotalSeconds(Date start, Date end) {
		return getTotalMilliseconds(start, end) / 1000.0;
	}

	//______________________________________________________________________
	public static final double getTotalMinutes(Date start, Date end) {
		return getTotalSeconds(start, end) / 60.0;
	}

	//______________________________________________________________________
	public static final double getTotalHours(Date start, Date end) {
		return getTotalSeconds(start, end) / 3600.0;
	}

	//______________________________________________________________________
	public static final Date addDays(Date start, int days) {
		return addHours(start, days * 24);
	}

	//______________________________________________________________________
	public static final Date addHours(Date start, int hours) {

		long timeToAdd = hours * 60 * 60 * 1000;
		return new Date(start.getTime() + timeToAdd);
	}
}
