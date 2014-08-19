package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;

public final class UUIDAdapter extends ValueAdapter<UUID> {

	public UUIDAdapter() {
		super(UUID.class);
	}

	@Override
	public final UUID parse(Culture culture, String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return UUID.fromString(input);
	}

	@Override
	public final String format(Culture culture, UUID input) {
		return input == null ? "" : input.toString();
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null;
	}

	@Override
	public final UUID convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (UUID.class.isInstance(value))
			return (UUID) value;
		else
			return UUID.fromString(value.toString());
	}

	@Override
	public int getSqlTypeId() {
		return java.sql.Types.VARCHAR;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, UUID value) throws SQLException {
		ps.setString(parameterIndex, value == null ? null : value.toString());
	}

	@Override
	public UUID readValue(ResultSet rs, int ordinal) throws SQLException {
		return parse(Cultures.EN_US, rs.getString(ordinal));
	}

}
