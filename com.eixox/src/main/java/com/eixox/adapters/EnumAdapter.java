package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;

@SuppressWarnings("rawtypes")
public final class EnumAdapter<T extends Enum> extends ValueAdapter<T> {

	public EnumAdapter(Class<T> enumClaz) {
		super(enumClaz);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T parse(Culture culture, String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return (T) Enum.valueOf(getDataType(), input);
	}

	@Override
	public final String format(Culture culture, T input) {
		return input == null ? "" : input.toString();
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "''");
		else {
			builder.append("'");
			builder.append(input.toString().replace("'", "''"));
			builder.append("'");
		}
	}

	@Override
	public final T readSql(ResultSet rs, int ordinal) throws SQLException {
		return parse(Cultures.EN_US, rs.getString(ordinal));
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T convert(Object value) {
		if (value == null)
			return null;
		else if (Enum.class.isInstance(value))
			return (T) value;
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return null;
	}

}
