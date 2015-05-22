package com.eixox.formatters;

import com.eixox.Formats;
import com.eixox.globalization.Culture;

public class CepFormatter implements ValueFormatter<Integer> {

	public static final String format(int value) {
		return Formats.cep(value);
	}

	public String format(Integer value, Culture culture) {
		if (value == null)
			return "";
		else
			return format(value);
	}

	public String formatObject(Object value, Culture culture) {
		if (value == null)
			return "";
		else
			return format((Integer) value);
	}

}
