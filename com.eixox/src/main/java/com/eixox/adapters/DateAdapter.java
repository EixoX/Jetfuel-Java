package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class DateAdapter implements ValueAdapter<Date> {

	public final DateFormat dateFormat;

	public DateAdapter(DateFormat format) {
		this.dateFormat = format;
	}

	public DateAdapter(String format) {
		this.dateFormat = new SimpleDateFormat(format);
	}

	public final Class<Date> getDataType() {
		return Date.class;
	}

	public final String format(Date value) {
		return this.dateFormat.format(value);
	}

	public final Date parse(String input) {
		try {
			return this.dateFormat.parse(input);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			Date dt = this.dateFormat.parse(source);
			field.set(target, dt);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Object cell = source.getObject(position);
			Date dt = convert(cell);
			field.set(target, dt);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Object cell = source.getObject(name);
			Date dt = convert(cell);
			field.set(target, dt);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Object cell = field.get(source);
			Date dt = (Date) cell;
			Timestamp ts = dt == null ? null : new Timestamp(dt.getTime());
			target.setTimestamp(position, ts);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void formatSql(Date source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(new Timestamp(source.getTime()));
			target.append('\'');
		}

	}

	public final Date convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Date)
			return (Date) source;
		else if (source instanceof java.sql.Date)
			return (Date) source;
		else if (source instanceof Timestamp)
			return (Date) source;
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Number)
			return new Date(((Number) source).longValue());
		else if (source instanceof Instant)
			return Date.from((Instant) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Date.");

	}
	

	public final String formatObject(Object value) {
		return format((Date) value);
	}


}
