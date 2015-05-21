package com.eixox.formatters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatter {

	private static final SimpleDateFormat FORMAT_YMD = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat PTBR_DATE = new SimpleDateFormat("dd/MM/yyyy");
	private static SimpleDateFormat PTBR_DATE_TIME = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

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

	public static final String ptBrDate(Object date) {
		if (date == null)
			return "";
		else
			return PTBR_DATE.format(date);
	}

	public static final String ptBrDateTime(Object date) {
		if (date == null)
			return "";
		else
			return PTBR_DATE_TIME.format(date);
	}

}
