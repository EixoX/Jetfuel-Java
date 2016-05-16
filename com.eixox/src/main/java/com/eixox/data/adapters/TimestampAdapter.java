package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
		else
			return Timestamp.valueOf(input);
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

}
