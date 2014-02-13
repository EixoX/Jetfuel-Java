package com.eixox.data;

import java.util.Date;

public final class ValueHelper {

	public static final boolean isNullOrEmpty(Object value) {
		if (value == null)
			return true;
		else if (value instanceof Number) {
			return ((Number) value).longValue() == 0;
		} else if (value instanceof Date) {
			return ((Date) value).getTime() == 0;
		} else if (value instanceof String) {
			return ((String) value).isEmpty();
		} else
			return false;
	}

}
