package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TimeAdapter implements ValueAdapter<Time> {

	public Class<Time> getDataType() {
		return Time.class;
	}

	public String format(Time value) {
		return value.toString();
	}

	public Time parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return Time.valueOf(input);
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			Time time = parse(source);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Time time = source.getTime(position);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Time time = source.getTime(name);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Time time = (Time) field.get(source);
			target.setTime(position, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void formatSql(Time source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

}
