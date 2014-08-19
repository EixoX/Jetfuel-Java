package com.eixox.formatters;

public class ValueFormatters {

	@SuppressWarnings("unchecked")
	public static <T> ValueFormatter<T> getFormatter(Class<T> claz) {

		if (claz != null && ValueFormatter.class.isAssignableFrom(claz))
		{
			try {
				return (ValueFormatter<T>) claz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		else
			return null;
	}

	public static <T> ValueFormatter<T> getFormatter(Class<T> claz, String formatString)
	{
		ValueFormatter<T> formatter = getFormatter(claz);
		if (formatter != null)
			return formatter;
		else if (formatString != null && !formatString.isEmpty())
			return new StringFormatter<T>(formatString);
		else
			return null;
	}
}
