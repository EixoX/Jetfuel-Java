package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BooleanAdapter implements ValueAdapter<Boolean> {

	public Class<Boolean> getDataType() {
		return Boolean.TYPE;
	}

	public String format(Boolean value) {
		return value.toString();
	}

	public boolean parseBoolean(String input) {
		return input == null || input.isEmpty() ? false : Boolean.parseBoolean(input);
	}

	public Boolean parse(String input) {
		return parseBoolean(input);
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			boolean b = parseBoolean(source);
			field.setBoolean(target, b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			boolean b = source.getBoolean(position);
			field.setBoolean(target, b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			boolean b = source.getBoolean(name);
			field.setBoolean(target, b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			boolean b = field.getBoolean(source);
			target.setBoolean(position, b);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void formatSql(Boolean source, StringBuilder target) {
		target.append(source);
	}

}
