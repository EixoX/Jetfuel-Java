package com.eixox.data.adapters;

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
}
