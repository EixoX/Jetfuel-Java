package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class CharacterAdapter extends ValueAdapter<Character> {

	public CharacterAdapter() {
		super(Character.class);
	}

	@Override
	public final Character parse(Culture culture, String input) {
		return input == null || input.isEmpty() ? (char) 0 : input.charAt(0);
	}

	@Override
	public final String format(Culture culture, Character input) {
		return input == null ? "" : input.toString();
	}

	public static final Character ZERO = Character.valueOf((char) 0);

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ZERO.equals(item);
	}

	@Override
	public final Character convert(Object value, Culture culture) {
		if (value == null)
			return (char)0;
		else if (Character.class.isInstance(value) || Character.TYPE.isInstance(value))
			return (Character) value;
		else if (Number.class.isInstance(value))
			return (char) ((Number) value).intValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.CHAR;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Character value) throws SQLException {
		ps.setString(parameterIndex, value == null ? null : value.toString());
	}

	@Override
	public Character readValue(ResultSet rs, int ordinal) throws SQLException {
		String string = rs.getString(ordinal);
		return string == null || string.isEmpty() ? null : string.charAt(0);
	}
}
