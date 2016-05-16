package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public final class NumberAdapter implements ValueAdapter<Number> {

	public final NumberFormat numberFormat;

	public NumberAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public NumberAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public NumberAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Number> getDataType() {
		return Number.class;
	}

	public final String format(Number input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Number parse(String input) {
		if (input == null || input.isEmpty())
			return new Double(0.0);
		else
			try {
				return this.numberFormat.parse(input);
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			Object d = parse(source);
			field.set(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			Object d = source.getObject(position);
			field.set(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			Object d = source.getObject(name);
			field.set(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			Object d = field.get(source);
			target.setObject(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Number source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}

	public static final NumberAdapter ENGLISH = new NumberAdapter(Locale.ENGLISH);

}
