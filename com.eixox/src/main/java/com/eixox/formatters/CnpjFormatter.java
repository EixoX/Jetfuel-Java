package com.eixox.formatters;

import com.eixox.Strings;
import com.eixox.globalization.Culture;

public class CnpjFormatter implements ValueFormatter<Long> {

	public String format(Long value, Culture culture) {
		long l = value;
		return Strings.concat(
				((l / 1000000000000L) % 1000),
				".",
				((l / 1000000000L) % 1000),
				".",
				((l / 1000000L) % 1000),
				"/",
				((l / 100L) % 10000),
				"-",
				l % 100);
	}

	public String formatObject(Object value, Culture culture) {
		return format((Long)value, culture);
	}

}
