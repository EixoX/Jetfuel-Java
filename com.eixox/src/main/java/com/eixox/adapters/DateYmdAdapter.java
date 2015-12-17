package com.eixox.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.eixox.globalization.Culture;

public class DateYmdAdapter extends ValueAdapter<Date> {

	public static final DateYmdAdapter INSTANCE = new DateYmdAdapter();

	public DateYmdAdapter() {
		super(Date.class);
	}

	public static final SimpleDateFormat FORMAT_YMD = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat FORMAT_YMDH = new SimpleDateFormat("yyyyMMddHH");
	public static final SimpleDateFormat FORMAT_YMDHM = new SimpleDateFormat("yyyyMMddHHmm");
	public static final SimpleDateFormat FORMAT_YMDHMS = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public Date parse(Culture culture, String input) {
		if (input.length() < 8)
			return null;
		try {

			switch (input.length()) {
			case 8:
				return FORMAT_YMD.parse(input);
			case 10:
				return FORMAT_YMDH.parse(input);
			case 12:
				return FORMAT_YMDHM.parse(input);
			default:
				return FORMAT_YMDHMS.parse(input);
			}
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public int getSqlTypeId() {
		return Types.TIMESTAMP;
	}

	@Override
	public void setParameterValue(PreparedStatement ps, int parameterIndex, Date value) throws SQLException {
		ps.setDate(parameterIndex, new java.sql.Date(value.getTime()));
	}

	@Override
	public Date readValue(ResultSet rs, int ordinal) throws SQLException {
		return rs.getDate(ordinal);
	}

	@Override
	public String format(Culture culture, Date input) {
		return FORMAT_YMDHMS.format(input);
	}

	@Override
	public boolean IsNullOrEmpty(Object item) {
		return item == null || ((Date) item).getTime() == 0L;
	}

	@Override
	public Date convert(Object value, Culture culture) {
		if (value == null)
			return null;

		Class<?> claz = value.getClass();

		if (Date.class.isAssignableFrom(claz))
			return (Date) value;

		else if (Number.class.isAssignableFrom(claz))
			return new Date(((Number) value).longValue());

		else
			return parse(value.toString());

	}

	public static final Date parseDate(String input) {
		return INSTANCE.parse(input);
	}

	public static final Timestamp parseTimestamp(String input) {
		Date dt = INSTANCE.parse(input);
		return dt == null ? null : new Timestamp(dt.getTime());
	}

}
