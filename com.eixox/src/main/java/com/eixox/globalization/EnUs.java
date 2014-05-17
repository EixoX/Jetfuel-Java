package com.eixox.globalization;

import java.util.Locale;

public class EnUs extends Culture {

	public EnUs() {
		super(Locale.US);
	}

	@Override
	public final String getLongDateFormat() {
		return "MMMM, dd YYYY";
	}

	@Override
	public final String getShortDateFormat() {
		return "MM/dd/yyyy";
	}

	@Override
	public final String getDateTimeFormat() {
		return "MM/dd/yyyy HH:mm:ss";
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
