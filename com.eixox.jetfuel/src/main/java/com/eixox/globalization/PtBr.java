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

	
	public final String getBaseLocaleID() {
		return "pt-BR";
	}

	
	public final String getDisplayName() {
		return "Portuguese (Brazil)";
	}

	
	public final String getDisplayVariant() {
		return "Português Brasileiro";
	}

	
	public final String getISO3LanguageCode() {
		return "por";
	}

	
	public final String getISO3CountryCode() {
		return "bra";
	}

	
	public final String getICULocale() {
		return "pt-BR";
	}

	
	public final Locale getLocale() {
		return locale;
	}

	
	public final String getCurrencySymbol() {
		return "R$";
	}

	
	public final char getDecimalSeparator() {
		return ',';
	}

	
	public final char getDigitSymbol() {
		return '#';
	}

	
	public final char getExponentSeparator() {
		return '^';
	}

	
	public final char getGroupingSeparator() {
		return '.';
	}

	
	public final char getInfinitySymbol() {
		return 'i';
	}

	
	public final String getInternationalCurrency() {
		return "BRL";
	}

	
	public final char getMinusSign() {
		return '-';
	}

	
	public final char getMonetaryDecimalSeparator() {
		return ',';
	}

	
	public final char getNaNSymbol() {
		return 'N';
	}

	
	public final char getPatternSeparator() {
		return ';';
	}

	
	public final char getPercentSymbol() {
		return '%';
	}

	
	public final char getPerMillSymbol() {
		return 'p';
	}

	
	public final char getZeroDigit() {
		return '0';
	}

	
	public final String[] getAMPMStrings() {
		return new String[] { "AM", "PM" };
	}

	
	public final String[] getEras() {
		return new String[] { "AC", "DC" };
	}

	
	public final String[] getMonthNames() {
		return new String[] { "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro",
				"Outubro", "Novembro", "Dezembro" };
	}

	
	public final String[] getShortMonthNames() {
		return new String[] { "Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez" };
	}

	
	public final String[] getWeekdays() {
		return new String[] { "Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira",
				"Sábado" };
	}

	
	public final String[] getShortWeekDays() {
		return new String[] { "Dom", "Seg", "Ter", "Qua", "Qui", "Sex", "Sáb" };
	}

	
	public final NumberFormat getCurrencyFormat() {
		return NumberFormat.getCurrencyInstance(getLocale());
	}

	
	public final NumberFormat getNumberFormat() {
		return NumberFormat.getNumberInstance(getLocale());
	}

	
	public final DateFormat getShortDateFormat() {
		return shortDateFormat;
	}

	
	public final DateFormat getShortDateTimeFormat() {
		return shortDateTimeFormat;
	}

	
	public final DateFormat getMediumDateFormat() {
		return mediumDateFormat;
	}

	
	public final DateFormat getMediumDateTimeFormat() {
		return mediumDateTimeFormat;
	}

	
	public final DateFormat getLongDateFormat() {
		return longDateFormat;
	}

	
	public final DateFormat getLongDateTimeFormat() {
		return longDateTimeFormat;
	}

}
