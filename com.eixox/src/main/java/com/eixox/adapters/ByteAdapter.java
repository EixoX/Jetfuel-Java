package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class ByteAdapter extends ValueAdapter<Byte> {

	public ByteAdapter() {
		super(Byte.class);
	}

	@Override
	public final Byte parse(Culture culture, String input) {
		Number n = culture.parseNumber(input).byteValue();
		return n == null ? null : n.byteValue();
	}

	@Override
	public final String format(Culture culture, Byte input) {
		return culture.formatNumber(input);
	}

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append(nullable ? "NULL" : "0");
		else
			builder.append((Byte) input);
	}

	@Override
	public final Byte readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getByte(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Byte) item) == 0;
	}

	@Override
	public final Byte convert(Object value) {
		if (value == null)
			return null;
		else if (Byte.class.isInstance(value) || Byte.TYPE.isInstance(value))
			return (Byte) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).byteValue();
		else if (String.class.isInstance(value))
			return parse((String) value);
		else
			return 0;
	}

}
