package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;

public class DoubleAdapter implements ValueAdapter<Double> {

	public final NumberFormat numberFormat;

	public DoubleAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public DoubleAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public DoubleAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Double> getDataType() {
		return Double.TYPE;
	}

	public final String format(Double input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Double parse(String input) {
		return parseDouble(input);
	}

	public final double parseDouble(String input) {
		if (input == null || input.isEmpty())
			return 0.0;
		else
			try {
				return this.numberFormat.parse(input).doubleValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			double d = parseDouble(source);
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			double d = source.getDouble(position);
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			double d = source.getDouble(name);
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			double d = field.getDouble(source);
			target.setDouble(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Double source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}
	
	public final Double convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof Double)
			return (Double) source;
		else if (source instanceof Number)
			return ((Number) source).doubleValue();
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof Date)
			return (double) ((Date) source).getTime();
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to Double.");
	}
	

	public final String formatObject(Object value) {
		return format((Double) value);
	}



	public static final DoubleAdapter ENGLISH = new DoubleAdapter(Locale.ENGLISH);

	
}
