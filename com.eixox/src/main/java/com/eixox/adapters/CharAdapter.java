package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CharAdapter implements ValueAdapter<Character> {

	public Class<Character> getDataType() {
		return Character.TYPE;
	}

	public String format(Character value) {
		return value.toString();
	}

	public char parseChar(String input) {
		return input == null || input.isEmpty() ? (char) 0 : input.charAt(0);
	}

	public Character parse(String input) {
		return parseChar(input);
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			char c = parseChar(source);
			field.setChar(target, c);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			String s = source.getString(position);
			char c = s == null || s.isEmpty() ? (char) 0 : s.charAt(0);
			field.setChar(target, c);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			String s = source.getString(name);
			char c = s == null || s.isEmpty() ? (char) 0 : s.charAt(0);
			field.setChar(target, c);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			char c = field.getChar(source);
			target.setString(position, c == 0 ? "NULL" : Character.toString(c));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void formatSql(Character source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append("'");
			target.append(source.toString().replace("'", "''"));
			target.append("'");
		}
	}

	public final Character convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Character)
			return (Character) source;
		else if (source instanceof Number)
			return (char) ((Number) source).intValue();
		else if (source instanceof String) {
			String s = (String) source;
			return s.isEmpty() ? Character.MIN_VALUE : s.charAt(0);
		} else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Character.");
	}
	

	public final String formatObject(Object value) {
		return format((Character) value);
	}

}
