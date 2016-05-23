package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.Time;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TimeAdapter implements ValueAdapter<Time> {

	public Class<Time> getDataType() {
		return Time.class;
	}

	public final String format(Time value) {
		return value.toString();
	}

	public final Time parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return Time.valueOf(input);
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			Time time = parse(source);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Time time = source.getTime(position);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Time time = source.getTime(name);
			field.set(target, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Time time = (Time) field.get(source);
			target.setTime(position, time);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Time source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

	public final Time convert(Object source) {
		if(source == null)
			return null;
		else if(source instanceof Time)
			return ((Time)source);
		else if(source instanceof Number)
			return new Time(((Number)source).longValue());
		else if(source instanceof Date)
			return new Time(((Date)source).getTime());
		else if(source instanceof String)
			return parse((String)source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Time.");
	}
	

	public final String formatObject(Object value) {
		return format((Time) value);
	}


}
