package com.eixox.data;

import java.sql.ResultSet;
import java.util.Locale;

public interface ValueAdapter<T> {

	T convert(Object value);

	T parse(String text);

	T parse(String text, Locale locale);

	String format(Object value);

	String format(Object value, Locale locale);

	String formatSql(Object object);

	T readFrom(ResultSet rs, int ordinal);

}
