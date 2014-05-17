package com.eixox.formatters;

import com.eixox.globalization.Culture;

public class StringFormatter<T> implements ValueFormatter<T> {

	private final String formatString;

	public StringFormatter(String formatString) {
		this.formatString = formatString;
	}

	public StringFormatter(StringFormat attribute) {
		this(attribute.format());
	}

	public String format(T value, Culture culture) {
		return String.format(culture.getLocale(), formatString, value);
	}

}
