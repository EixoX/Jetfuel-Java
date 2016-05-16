package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public final class FloatAdapter implements ValueAdapter<Float> {

	public final NumberFormat numberFormat;

	public FloatAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public FloatAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public FloatAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Float> getDataType() {
		return Float.TYPE;
	}

	public final String format(Float input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Float parse(String input) {
		return parseFloat(input);
	}

	public final float parseFloat(String input) {
		if (input == null || input.isEmpty())
			return 0.0f;
		else
			try {
				return this.numberFormat.parse(input).floatValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			float d = parseFloat(source);
			field.setFloat(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			float d = source.getFloat(position);
			field.setFloat(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			float d = source.getFloat(name);
			field.setFloat(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			float d = field.getFloat(source);
			target.setFloat(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Float source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}

	public static final FloatAdapter ENGLISH = new FloatAdapter(Locale.ENGLISH);

}
