package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Date;

public class TimestampAdapter implements ValueAdapter<Timestamp> {

	public Class<Timestamp> getDataType() {
		return Timestamp.class;
	}

	public String format(Timestamp value) {
		return value.toString();
	}

	public Timestamp parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else if (input.length() > 10)
			return Timestamp.valueOf(input);
		else
			return new Timestamp(java.sql.Date.valueOf(input).getTime());

	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			Timestamp timestamp = parse(source);
			field.set(target, timestamp);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Timestamp timestamp = source.getTimestamp(position);
			field.set(target, timestamp);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Timestamp timestamp = source.getTimestamp(name);
			field.set(target, timestamp);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Timestamp timestamp = (Timestamp) field.get(source);
			target.setTimestamp(position, timestamp);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void formatSql(Timestamp source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

	public final Timestamp convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Timestamp)
			return ((Timestamp) source);
		else if (source instanceof Number)
			return new Timestamp(((Number) source).longValue());
		else if (source instanceof Date)
			return new Timestamp(((Date) source).getTime());
		else if (source instanceof String)
			return parse((String) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Timestamp.");
	}

	public final String formatObject(Object value) {
		return format((Timestamp) value);
	}

}
