package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

public class DateSqlAdapter implements ValueAdapter<Date> {

	public Class<Date> getDataType() {
		return Date.class;
	}

	public final String format(Date value) {
		return value.toString();
	}

	public final Date parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return Date.valueOf(input);
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			Date date = parse(source);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Date date = source.getDate(position);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Date date = source.getDate(name);
			field.set(target, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Date date = (Date) field.get(source);
			target.setDate(position, date);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Date source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

	public final Date convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Date)
			return (Date) source;
		else if (source instanceof java.util.Date)
			return new Date(((java.util.Date) source).getTime());
		else if (source instanceof Timestamp)
			return new Date(((Timestamp) source).getTime());
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Number)
			return new Date(((Number) source).longValue());
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to SqlDate.");
	}


	public final String formatObject(Object value) {
		return format((Date) value);
	}

}
