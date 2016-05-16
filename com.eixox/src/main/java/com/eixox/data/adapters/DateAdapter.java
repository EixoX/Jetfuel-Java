package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DateAdapter implements ValueAdapter<Date> {

	public Class<Date> getDataType() {
		return Date.class;
	}

	public String format(Date value) {
		return value.toString();
	}

	public Date parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return Date.valueOf(input);
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			Date date = parse(source);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Date date = source.getDate(position);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Date date = source.getDate(name);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Date date = (Date) field.get(source);
			target.setDate(position, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void formatSql(Date source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

}
