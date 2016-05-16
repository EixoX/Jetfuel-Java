package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.NumberFormat;
import java.util.Locale;

public final class ByteAdapter implements ValueAdapter<Byte> {

	public final NumberFormat numberFormat;

	public ByteAdapter(NumberFormat format) {
		this.numberFormat = format;
	}

	public ByteAdapter(Locale locale) {
		this.numberFormat = NumberFormat.getNumberInstance(locale);
	}

	public ByteAdapter() {
		this(NumberFormat.getNumberInstance());
	}

	public final Class<Byte> getDataType() {
		return Byte.TYPE;
	}

	public final String format(Byte input) {
		return input == null ? "" : this.numberFormat.format(input);
	}

	public final Byte parse(String input) {
		return parseByte(input);
	}

	public final byte parseByte(String input) {
		if (input == null || input.isEmpty())
			return 0;
		else
			try {
				return this.numberFormat.parse(input).byteValue();
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			byte d = parseByte(source);
			field.setByte(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			byte d = source.getByte(position);
			field.setByte(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			byte d = source.getByte(name);
			field.setByte(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			byte d = field.getByte(source);
			target.setByte(position, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(Byte source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else
			target.append(ENGLISH.numberFormat.format(source));
	}

	public static final ByteAdapter ENGLISH = new ByteAdapter(Locale.ENGLISH);

}
