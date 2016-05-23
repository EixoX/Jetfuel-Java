package com.eixox.adapters;

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

	public final Boolean convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Boolean)
			return (Boolean) source;
		else if (source instanceof Number)
			return ((Number) source).intValue() != 0;
		else if (source instanceof String)
			return parse((String) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Boolean.");
	}

	public final String formatObject(Object value) {
		return format((Boolean) value);
	}

}
