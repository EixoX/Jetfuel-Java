package com.eixox.globalization;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public final class PtBr implements Culture {

	private static final Locale locale = new Locale("pt", "br");
	private static final SimpleDateFormat shortDateFormat = new SimpleDateFormat("dd/MM/yyyy", locale);
	private static final SimpleDateFormat shortDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", locale);
	private static final SimpleDateFormat mediumDateFormat = new SimpleDateFormat("dd MMM yyyy", locale);
	private static final SimpleDateFormat mediumDateTimeFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS",
			locale);
	private static final SimpleDateFormat longDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS", locale);
	private static final SimpleDateFormat longDateTimeFormat = new SimpleDateFormat("dd MMM yyyy", locale);

	@Override
	public final String getBaseLocaleID() {
		return "pt-BR";
	}

	@Override
	public final String getDisplayName() {
		return "Portuguese (Brazil)";
	}

	@Override
	public final String getDisplayVariant() {
		return "Português Brasileiro";
	}

	@Override
	public final String getISO3LanguageCode() {
		return "por";
	}

	@Override
	public final String getISO3CountryCode() {
		return "bra";
	}

	@Override
	public final String getICULocale() {
		return "pt-BR";
	}

	@Override
	public final Locale getLocale() {
		return locale;
	}

	@Override
	public final String getCurrencySymbol() {
		return "R$";
	}

	@Override
	public final char getDecimalSeparator() {
		return ',';
	}

	@Override
	public final char getDigitSymbol() {
		return '#';
	}

	@Override
	public final char getExponentSeparator() {
		return '^';
	}

	@Override
	public final char getGroupingSeparator() {
		return '.';
	}

	@Override
	public final char getInfinitySymbol() {
		return 'i';
	}

	@Override
	public final String getInternationalCurrency() {
		return "BRL";
	}

	@Override
	public final char getMinusSign() {
		return '-';
	}

	@Override
	public final char getMonetaryDecimalSeparator() {
		return ',';
	}

	@Override
	public final char getNaNSymbol() {
		return 'N';
	}

	@Override
	public final char getPatternSeparator() {
		return ';';
	}

	@Override
	public final char getPercentSymbol() {
		return '%';
	}

	@Override
	public final char getPerMillSymbol() {
		return 'p';
	}

	@Override
	public final char getZeroDigit() {
		return '0';
	}

	@Override
	public final String[] getAMPMStrings() {
		return new String[] { "AM", "PM" };
	}

	@Override
	public final String[] getEras() {
		return new String[] { "AC", "DC" };
	}

	@Override
	public final String[] getMonthNames() {
		return new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
	}

	@Override
	public final String[] getShortMonthNames() {
		return new String[] { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
	}

	@Override
	public final String[] getWeekdays() {
		return new String[] { "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
				"Sábado" };
	}

	@Override
	public final String[] getShortWeekDays() {
		return new String[] { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb" };
	}

	@Override
	public final NumberFormat getCurrencyFormat() {
		return NumberFormat.getCurrencyInstance(getLocale());
	}

	@Override
	public final NumberFormat getNumberFormat() {
		return NumberFormat.getNumberInstance(getLocale());
	}

	@Override
	public final DateFormat getShortDateFormat() {
		return shortDateFormat;
	}

	@Override
	public final DateFormat getShortDateTimeFormat() {
		return shortDateTimeFormat;
	}

	@Override
	public final DateFormat getMediumDateFormat() {
		return mediumDateFormat;
	}

	@Override
	public final DateFormat getMediumDateTimeFormat() {
		return mediumDateTimeFormat;
	}

	@Override
	public final DateFormat getLongDateFormat() {
		return longDateFormat;
	}

	@Override
	public final DateFormat getLongDateTimeFormat() {
		return longDateTimeFormat;
	}

}
