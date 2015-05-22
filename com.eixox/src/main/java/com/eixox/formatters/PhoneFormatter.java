package com.eixox.formatters;

import com.eixox.Strings;
import com.eixox.globalization.Culture;

public class PhoneFormatter implements ValueFormatter<Long> {

	public static final String formatPtBr(long ddi, long ddd, long prefix, long sufix) {
		if (sufix <= 0 && prefix <= 0)
			return "";
		else if (ddi > 0)
			return Strings.concat("+", ddi, " (", ddd, ") ", prefix, "-", sufix);
		else if (ddd > 0)
			return Strings.concat("(", ddd, ") ", prefix, "-", sufix);
		else
			return Strings.concat(prefix, "-", sufix);
	}

	public static final String formatPtBr(long phone) {
		return formatPtBr(phone / 100000000000L, (phone / 1000000000L) % 100, (phone / 10000L) % 100000, phone % 10000L);
	}

	public String format(Long value, Culture culture) {
		if (value == null)
			return "";
		else
			return formatPtBr(value);

	}

	public String formatObject(Object value, Culture culture) {
		if (value == null)
			return "";
		else
			return formatPtBr((Long) value);
	}
}
