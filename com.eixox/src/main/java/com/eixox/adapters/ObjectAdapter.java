package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class ObjectAdapter implements ValueAdapter<Object> {

	public abstract Class<Object> getDataType();

	public String format(Object value) {
		return value == null ? "" : value.toString();
	}

	public abstract Object parse(String input);

	public void parseIntoField(String source, Field field, Object target) {
		try {
			Object o = parse(source);
			field.set(target, o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Object o = source.getObject(position);
			field.set(target, o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Object o = source.getObject(name);
			field.set(target, o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Object o = field.get(source);
			target.setObject(position, o);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public abstract void formatSql(Object source, StringBuilder target);

}
