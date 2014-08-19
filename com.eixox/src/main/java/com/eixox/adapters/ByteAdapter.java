package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class ByteAdapter extends ValueAdapter<Byte> {

	public ByteAdapter() {
		super(Byte.class);
	}

	@Override
	public final Byte parse(Culture culture, String input) {
		Number n = culture.parseNumber(input);
		return n == null ? 0 : n.byteValue();
	}

	@Override
	public final String format(Culture culture, Byte input) {
		return culture.formatNumber(input);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Byte) item) == 0;
	}

	@Override
	public final Byte convert(Object value, Culture culture) {
		if (value == null)
			return 0;
		else if (Byte.class.isInstance(value) || Byte.TYPE.isInstance(value))
			return (Byte) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).byteValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return 0;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.TINYINT;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Byte value) throws SQLException {
		ps.setByte(parameterIndex, value);
	}

	@Override
	public Byte readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getByte(ordinal);
	}

}
