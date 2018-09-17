package com.eixox.formatters;

import com.eixox.Strings;
import com.eixox.globalization.Culture;

public class CpfFormatter implements ValueFormatter<Long> {

	public static String format(long l) {
		return Strings.concat(
				((l / 100000000L) % 1000),
				".",
				((l / 100000L) % 1000),
				".",
				((l / 100L) % 1000),
				"-",
				(l % 100 == 0 ? "00" : l % 100));
	}

	public String format(Long value, Culture culture) {
		if (value == null)
			return "";
		else
			return format(value);
	}

	public String formatObject(Object value, Culture culture) {
		if (value == null)
			return "";
		else
			return format((Long) value);
	}
}
