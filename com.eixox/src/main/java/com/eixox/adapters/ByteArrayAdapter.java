package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.eixox.Base64;

public class ByteArrayAdapter implements ValueAdapter<byte[]> {

	public static final byte[] EMPTY = new byte[] {};

	@SuppressWarnings("unchecked")
	public final Class<byte[]> getDataType() {
		return (Class<byte[]>) EMPTY.getClass();
	}

	public final String format(byte[] value) {
		return value == null ? null : Base64.encode(value);
	}

	public final byte[] parse(String input) {
		return input == null || input.isEmpty() ? null : Base64.decode(input);
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			byte[] arr = parse(source);
			field.set(target, arr);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			byte[] arr = parse(source.getString(position));
			field.set(target, arr);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			byte[] arr = parse(source.getString(name));
			field.set(target, arr);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			byte[] arr = (byte[]) field.get(source);
			target.setString(position, format(arr));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void formatSql(byte[] source, StringBuilder target) {
		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(format(source));
			target.append('\'');
		}
	}

	public final byte[] convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof byte[])
			return (byte[]) source;
		else if (source instanceof Number)
			return new byte[] { ((Number) source).byteValue() };
		else if (source instanceof String)
			return parse((String) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to byte[]");
	}
	

	public final String formatObject(Object value) {
		return format((byte[]) value);
	}


}
