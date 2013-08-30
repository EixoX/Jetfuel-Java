package com.eixox.globalization;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

public interface Culture {

	public String getBaseLocaleID();

	public String getDisplayName();

	public String getDisplayVariant();

	public String getISO3LanguageCode();

	public String getISO3CountryCode();

	public String getICULocale();

	public Locale getLocale();

	public String getCurrencySymbol();

	public char getDecimalSeparator();

	public char getDigitSymbol();

	public char getExponentSeparator();

	public char getGroupingSeparator();

	public char getInfinitySymbol();

	public String getInternationalCurrency();

	public char getMinusSign();

	public char getMonetaryDecimalSeparator();

	public char getNaNSymbol();

	public char getPatternSeparator();

	public char getPercentSymbol();

	public char getPerMillSymbol();

	public char getZeroDigit();

	public String[] getAMPMStrings();

	public String[] getEras();

	public String[] getMonthNames();

	public String[] getShortMonthNames();

	public String[] getWeekdays();

	public String[] getShortWeekDays();

	public NumberFormat getCurrencyFormat();

	public NumberFormat getNumberFormat();

	public DateFormat getShortDateFormat();

	public DateFormat getShortDateTimeFormat();

	public DateFormat getMediumDateFormat();

	public DateFormat getMediumDateTimeFormat();

	public DateFormat getLongDateFormat();

	public DateFormat getLongDateTimeFormat();

}
