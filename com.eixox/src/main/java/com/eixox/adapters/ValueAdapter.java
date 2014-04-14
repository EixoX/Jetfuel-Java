package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;

public abstract class ValueAdapter<T> {

	private final Class<T>	dataType;

	protected ValueAdapter(Class<T> dataType) {
		this.dataType = dataType;
	}

	public final Class<T> getDataType() {
		return this.dataType;
	}

	public abstract T parse(Culture culture, String input);

	public final T parse(String input) {
		return parse(Cultures.EN_US, input);
	}

	public final Object parseObject(String input) {
		return parse(Cultures.EN_US, input);
	}

	public abstract String format(Culture culture, T input);

	public final String format(T input) {
		return format(Cultures.EN_US, input);
	}

	public abstract void appendSql(StringBuilder builder, Object input, boolean nullable);

	public abstract T readSql(ResultSet rs, int ordinal) throws SQLException;

	public final void appendSqlIterable(StringBuilder builder, Iterable<?> iterable) {
		builder.append("(");
		if (iterable != null) {
			boolean prependComma = false;
			for (Object o : iterable) {
				if (prependComma)
					builder.append(", ");
				else
					prependComma = true;
				appendSql(builder, o, true);
			}
		}
		builder.append(")");
	}

	public abstract boolean IsNullOrEmpty(Object item);

	public abstract T convert(Object value);

}
