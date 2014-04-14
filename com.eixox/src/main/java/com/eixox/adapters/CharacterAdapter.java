package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;

public final class CharacterAdapter extends ValueAdapter<Character> {

	public CharacterAdapter() {
		super(Character.class);
	}

	@Override
	public final Character parse(Culture culture, String input) {
		return input == null || input.isEmpty() ? null : input.charAt(0);
	}

	@Override
	public final String format(Culture culture, Character input) {
		return input == null ? "" : input.toString();
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "''");
		else {
			builder.append('\'');
			builder.append(input.toString().replace("'", "''"));
			builder.append('\'');
		}
	}

	@Override
	public final Character readSql(ResultSet rs, int ordinal) throws SQLException {
		return parse(Cultures.EN_US, rs.getString(ordinal));
	}

	public static final Character	ZERO	= Character.valueOf((char) 0);

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ZERO.equals(item);
	}

	@Override
	public final Character convert(Object value) {
		if (value == null)
			return null;
		else if (Character.class.isInstance(value) || Character.TYPE.isInstance(value))
			return (Character) value;
		else if (Number.class.isInstance(value))
			return (char) ((Number) value).intValue();
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0;
	}
}
