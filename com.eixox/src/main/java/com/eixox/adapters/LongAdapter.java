package com.eixox.adapters;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;

public final class LongAdapter extends ValueAdapter<Long> {

	public LongAdapter() {
		super(Long.class);
	}

	@Override
	public final Long parse(Culture culture, String input) {
		Number n = culture.parseNumber(input);
		return n == null ? 0L : n.longValue();
	}

	@Override
	public final String format(Culture culture, Long input) {
		return culture.formatNumber(input);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ((Long) item) == 0L;
	}

	@Override
	public final Long convert(Object value, Culture culture) {
		if (value == null)
			return 0L;
		else if (Long.class.isInstance(value) || Long.TYPE.isInstance(value))
			return (Long) value;
		else if (Number.class.isInstance(value))
			return ((Number) value).longValue();
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else if (BigDecimal.class.isInstance(value))
			return ((BigDecimal) value).longValue();
		else
			return 0L;
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.BIGINT;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Long value) throws SQLException {
		ps.setLong(parameterIndex, value);
	}

	@Override
	public Long readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getLong(ordinal);
	}
}
