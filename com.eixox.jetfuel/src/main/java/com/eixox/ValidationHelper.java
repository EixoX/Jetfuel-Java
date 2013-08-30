package com.eixox;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class ValidationHelper {

	public static final boolean isNullOrEmpty(Object value) {
		if (value == null)
			return true;

		if (value instanceof String)
			return ((String) value).isEmpty();

		if (value instanceof Number)
			return ((Number) value).intValue() == 0;

		if (ReflectionHelper.isNumeric(value.getClass()))
			return ((Number) value).intValue() == 0;

		if (value instanceof Calendar)
			return ((Calendar) value).getTimeInMillis() == 0;

		if (value instanceof Date)
			return ((Date) value).getTime() == 0;

		if (List.class.isInstance(value))
			return ((List<?>) value).size() > 0;

		return false;
	}
}
