package com.eixox.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public final class UUIDAdapter implements ValueAdapter<UUID> {

	public final Class<UUID> getDataType() {
		return UUID.class;
	}

	public final String format(UUID value) {
		return value == null ? "" : value.toString();
	}

	public final UUID parse(String input) {
		return input == null || input.isEmpty() ? null : UUID.fromString(input);
	}

	public final void parseIntoField(String source, Field field, Object target) {
		try {
			UUID value = parse(source);
			field.set(target, value);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			UUID uid = parse(source.getString(position));
			field.set(target, uid);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			UUID uid = parse(source.getString(name));
			field.set(target, uid);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			UUID val = (UUID) field.get(source);
			String st = val == null ? null : val.toString();
			target.setString(position, st);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final void formatSql(UUID source, StringBuilder target) {

		if (source == null)
			target.append("NULL");
		else {
			target.append('\'');
			target.append(source.toString());
			target.append('\'');
		}

	}

	public final UUID convert(Object source) {
		if (source == null)
			return null;
		else if (source instanceof UUID)
			return ((UUID) source);
		else if (source instanceof String)
			return parse((String) source);
		else if (source instanceof byte[])
			return UUID.nameUUIDFromBytes((byte[]) source);
		else
			throw new RuntimeException("Can't convert " + source.getClass() + " to UUID.");
	}
	

	public final String formatObject(Object value) {
		return format((UUID) value);
	}


}
