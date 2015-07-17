package com.eixox.globalization;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public abstract class Culture {

	public static final DateTimeFormatter ISOFORMATTER = ISODateTimeFormat.dateTimeParser();

	private static final DateFormat rfc822Formatter = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss Z", Locale.US);

	private final NumberFormat numberFormatter;
	private final NumberFormat currencyFormatter;
	private final NumberFormat integerFormatter;
	private final NumberFormat percentFormatter;
	private final DateFormat longDateFormatter;
	private final DateFormat shortDateFormatter;
	private final DateFormat dateTimeFormatter;
	private final DateFormat timeFormatter;
	private final Locale locale;

	public abstract String getLongDateFormat();

	public abstract String getShortDateFormat();

	public abstract String getDateTimeFormat();

	public abstract String getTimeFormat();

	public abstract char getDateSeparator();

	public abstract char getTimeSeparator();

	public Culture(Locale locale) {
		this.locale = locale;
		this.numberFormatter = DecimalFormat.getInstance(locale);
		this.currencyFormatter = DecimalFormat.getCurrencyInstance(locale);
		this.integerFormatter = DecimalFormat.getIntegerInstance(locale);
		this.percentFormatter = DecimalFormat.getPercentInstance(locale);
		this.longDateFormatter = new SimpleDateFormat(getLongDateFormat(), locale);
		this.shortDateFormatter = new SimpleDateFormat(getShortDateFormat(), locale);
		this.dateTimeFormatter = new SimpleDateFormat(getDateTimeFormat(), locale);
		this.timeFormatter = new SimpleDateFormat(getTimeFormat(), locale);
	}

	public final Locale getLocale() {
		return this.locale;
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

	public final Date parseDateRfc822(String input) {
		if (input == null || input.isEmpty())
			return null;
		try {
			return rfc822Formatter.parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Date parseDateTime(String input) {
		if (input == null || input.isEmpty())
			return null;
		try {
			return this.dateTimeFormatter.parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Date parseDate(String input) {
		if (input == null || input.isEmpty())
			return null;
		try {
			if (input.indexOf(getDateSeparator()) > 0) {
				if (input.indexOf(getTimeSeparator()) > 0)
					return this.dateTimeFormatter.parse(input);
				else
					return this.shortDateFormatter.parse(input);
			} else if (input.indexOf(getTimeSeparator()) > 0) {
				return this.timeFormatter.parse(input);
			} else
				return this.longDateFormatter.parse(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
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

	public final String formatRfc822Date(Date input) {
		if (input == null)
			return "";
		else
			return rfc822Formatter.format(input);
	}

	public final String formatRcf822Date(Object input) {
		if (input == null)
			return "";
		else
			return rfc822Formatter.format(input);
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

	public final String formatDateTime(Date input) {
		if (input == null)
			return "";
		else
			return this.dateTimeFormatter.format(input);
	}

	public final String formatDateTime(Object input) {
		if (input == null)
			return "";
		else
			return this.dateTimeFormatter.format(input);
	}

	public final Date parseDateIso8601(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return ISOFORMATTER.parseDateTime(input).toDate();
	}
}
