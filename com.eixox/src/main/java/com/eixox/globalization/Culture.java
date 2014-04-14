package com.eixox.globalization;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Culture {

	private final NumberFormat	numberFormatter;
	private final NumberFormat	currencyFormatter;
	private final NumberFormat	integerFormatter;
	private final NumberFormat	percentFormatter;
	private final String		longDateFormat;
	private final DateFormat	longDateFormatter;
	private final String		shortDateFormat;
	private final DateFormat	shortDateFormatter;
	private final Locale		locale;
	private final String		rfc822Format;
	private final DateFormat	rfc822Formatter;

	public Culture() {
		this(Locale.US, "MMM, dd yyyy", "yyyy-MM-dd");
	}

	protected Culture(Locale locale, String longDateFormat, String shortDateFormat) {
		this.locale = locale;
		this.numberFormatter = DecimalFormat.getInstance(locale);
		this.currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
		this.integerFormatter = DecimalFormat.getIntegerInstance(locale);
		this.percentFormatter = DecimalFormat.getPercentInstance(locale);
		this.longDateFormat = longDateFormat;
		this.longDateFormatter = new SimpleDateFormat(longDateFormat);
		this.shortDateFormat = shortDateFormat;
		this.shortDateFormatter = new SimpleDateFormat(shortDateFormat);
		this.rfc822Format = "E, d MMM yyyy HH:mm:ss Z";
		this.rfc822Formatter = new SimpleDateFormat(rfc822Format, Locale.US);
	}

	public final Number parseNumber(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.numberFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Number perseCurrency(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.currencyFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Number perseInteger(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.integerFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Number parsePercent(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.percentFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Date parseLongDate(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.longDateFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Date parseShortDate(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			try {
				return this.shortDateFormatter.parse(input);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	}

	public final Date parseDateTime(String input) {
		if (input == null || input.isEmpty())
			return null;
		try {
			return this.rfc822Formatter.parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final String getLongDateFormat() {
		return longDateFormat;
	}

	public final String getShortDateFormat() {
		return shortDateFormat;
	}

	public final Locale getLocale() {
		return locale;
	}

	public final String formatNumber(long input) {
		return this.numberFormatter.format(input);
	}

	public final String formatNumber(double input) {
		return this.numberFormatter.format(input);
	}

	public final String formatNumber(Object input) {
		return input == null ? "" : this.numberFormatter.format(input);
	}

	public final String formatInteger(long input) {
		return this.integerFormatter.format(input);
	}

	public final String formatInteger(double input) {
		return this.integerFormatter.format(input);
	}

	public final String formatInteger(Object input) {
		return input == null ? "" : this.integerFormatter.format(input);
	}

	public final String formatCurrency(long input) {
		return this.currencyFormatter.format(input);
	}

	public final String formatCurrency(double input) {
		return this.currencyFormatter.format(input);
	}

	public final String formatCurrency(Object input) {
		return input == null ? "" : this.currencyFormatter.format(input);
	}

	public final String formatPercent(long input) {
		return this.percentFormatter.format(input);
	}

	public final String formatPercent(double input) {
		return this.percentFormatter.format(input);
	}

	public final String formatPercent(Object input) {
		return input == null ? "" : this.percentFormatter.format(input);
	}

	public final String formatDateTime(Date input) {
		if (input == null)
			return "";
		else
			return this.rfc822Formatter.format(input);
	}

	public final String formatDateTime(Object input) {
		if (input == null)
			return "";
		else
			return this.rfc822Formatter.format(input);
	}

	public final String formatLongDate(Date input) {
		if (input == null)
			return "";
		else
			return this.longDateFormatter.format(input);
	}

	public final String formatLongDate(Object input) {
		if (input == null)
			return "";
		else
			return this.longDateFormatter.format(input);
	}

	public final String formatShortDate(Date input) {
		if (input == null)
			return "";
		else
			return this.shortDateFormatter.format(input);
	}

	public final String formatShortDate(Object input) {
		if (input == null)
			return "";
		else
			return this.shortDateFormatter.format(input);
	}
}
