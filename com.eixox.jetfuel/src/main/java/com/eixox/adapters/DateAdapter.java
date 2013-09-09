package com.eixox.adapters;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateAdapter implements ValueAdapter<Date> {

	private DateFormat dateFormat = SimpleDateFormat.getInstance();
	
	public final DateFormat getDateFormat() {
		return dateFormat;
	}

	public final void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	
	public Date adapt(Object input) {
		if (input == null)
			return null;
		else if (input instanceof Date)
			return (Date) input;
		else if (input instanceof Calendar)
			return ((Calendar) input).getTime();
		else
			try {
				return dateFormat.parse(input.toString());
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
	}

	
	public Date parse(String input) {
		try {
			return dateFormat.parse(input.toString());
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	
	public String format(Date input) {
		return dateFormat.format(input);
	}

}
