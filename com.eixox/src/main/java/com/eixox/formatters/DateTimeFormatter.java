package com.eixox.formatters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatter {

	private static final SimpleDateFormat FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");

	public static final String formatSimpleDate(Date date, Locale locale) {
		if (date == null)
			return "";
		else
			return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(date);
	}

	public static final String formatYMD(Date date) {
		if (date == null)
			return "";
		else
			return FORMAT_YMD.format(date);
	}

}
