package com.eixox.globalization;

import java.util.Locale;

public class PtBr extends Culture {

	public PtBr() {
		super(new Locale("pt", "BR"));
	}

	@Override
	public final String getLongDateFormat() {
		return "dd 'de' MMMM 'de' yyyy";
	}

	@Override
	public final String getShortDateFormat() {
		return "dd/MM/yyyy";
	}

	@Override
	public final String getDateTimeFormat() {
		return "dd/MM/yyyy HH:mm:ss";
	}

	@Override
	public String getTimeFormat() {
		return "HH:mm:ss";
	}

	@Override
	public final char getDateSeparator() {
		return '/';
	}

	@Override
	public final char getTimeSeparator() {
		return ':';
	}

}
