package com.eixox.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.eixox.globalization.Culture;

public final class DateTimeAdapter extends ValueAdapter<Date> {

	public DateTimeAdapter() {
		super(Date.class);
	}

	@Override
	public final Date parse(Culture culture, String input) {
		return culture.parseDate(input);
	}

	@Override
	public final String format(Culture culture, Date input) {
		return input == null ? "" : culture.formatDateTime(input);
	}

	public static final DateFormat	SqlDateFormatter	= new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss.S''", Locale.US);

	@Override
	public final void appendSql(StringBuilder builder, Object input, boolean nullable) {
		if (input == null)
			builder.append("NULL");
		else
			builder.append(SqlDateFormatter.format((Date) input));
	}

	@Override
	public final Date readSql(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDate(ordinal);
	}

	@Override
	public final boolean IsNullOrEmpty(Object item) {
		return item == null || ZERO.equals(item);
	}

	@Override
	public final Date convert(Object value, Culture culture) {
		if (value == null)
			return null;
		else if (Date.class.isInstance(value))
			return (Date) value;
		else if (Number.class.isInstance(value))
		{
			Date dt = new Date();
			dt.setTime(((Number) value).longValue());
			return dt;
		}
		else if (String.class.isInstance(value))
			return parse(culture, (String) value);
		else
			return ZERO;
	}

	public static final Date	ZERO;
	static {
		ZERO = new Date();
		ZERO.setTime(0);
	}

}
