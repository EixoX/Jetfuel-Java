package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;

public abstract class ValueAdapter<T> {

	private final Class<T> dataType;

	protected ValueAdapter(Class<T> dataType) {
		this.dataType = dataType;
	}

	public final Class<T> getDataType() {
		return this.dataType;
	}

	public abstract T parse(Culture culture, String input);

	public abstract int getSqlTypeId();

	public abstract void setParameterValue(PreparedStatement ps, int parameterIndex, T value) throws SQLException;

	public abstract T readValue(ResultSet rs, int ordinal) throws SQLException;

	public abstract String format(Culture culture, T input);

	public abstract boolean IsNullOrEmpty(Object item);

	public abstract T convert(Object value, Culture culture);

	public final T parse(String input) {
		return parse(Cultures.EN_US, input);
	}

	public final Object parseObject(String input) {
		return parse(Cultures.EN_US, input);
	}

	public final String format(T input) {
		return format(Cultures.EN_US, input);
	}

	@SuppressWarnings("unchecked")
	public final String formatObject(Object input) {
		return format((T) input);
	}

	public final T convert(Object value) {
		return convert(value, Cultures.EN_US);
	}

	@SuppressWarnings("unchecked")
	public final void setParameter(PreparedStatement ps, int parameterIndex, Object value) throws SQLException {
		setParameterValue(ps, parameterIndex, (T) value);

	}

}
