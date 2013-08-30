package com.eixox.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class CalendarAdapter implements ValueAdapter<Calendar> {

	private static final DateFormat dateFormat = DateFormat.getInstance();

	@Override
	public Calendar adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Calendar) {
			return (Calendar) input;
		} else if (input instanceof Date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime((Date) input);
			return cal;
		} else {
			return parse(input.toString());
		}
	}

	@Override
	public Calendar parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else {
			try {
				Date dt = dateFormat.parse(input);
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				return cal;
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String format(Calendar input) {
		if (input == null)
			return "";
		else
			return dateFormat.format(input.getTime());
	}

}
