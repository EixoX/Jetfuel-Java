package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StringAdapter implements ValueAdapter<String> {

	public Class<String> getDataType() {
		return String.class;
	}

	public String format(String value) {
		return value;
	}

	public String parse(String input) {
		return input;
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			field.set(target, source);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Object s = source.getString(position);
			field.set(target, s == null ? null : s.toString());
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Object s = source.getString(name);
			field.set(target, s == null ? null : s.toString());
		} catch (Exception ex) {
			throw new RuntimeException();
		}
	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Object s = field.get(source);
			target.setString(position, s == null ? null : s.toString());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void formatSql(String source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.replace("'", "''"));
			target.append('\'');
		}
	}

	public static final String concat(String... items) {
		int sz = 0;
		for (int i = 0; i < items.length; i++)
			sz += items[i].length();
		final StringBuilder builder = new StringBuilder(sz);
		for (int i = 0; i < items.length; i++)
			builder.append(items[i]);
		return builder.toString();
	}

	public static final String right(String input, int length) {
		if (input == null || input.isEmpty())
			return input;
		else if (length >= input.length())
			return input;
		else
			return input.substring(input.length() - length);
	}
}
