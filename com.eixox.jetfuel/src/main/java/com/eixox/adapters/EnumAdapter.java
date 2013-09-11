package com.eixox.adapters;

@SuppressWarnings("rawtypes")
public class EnumAdapter<T extends Enum> implements ValueAdapter<T> {

	private final Class<T> enumClass;

	public EnumAdapter(Class<T> claz) {
		this.enumClass = claz;
	}

	@SuppressWarnings("unchecked")
	public final T adapt(Object input) {
		if (input == null)
			return null;
		else if (enumClass.isAssignableFrom(input.getClass()))
			return (T) input;
		else
			return parse(input.toString());
	}

	@SuppressWarnings("unchecked")
	public final T parse(String input) {
		if (input == null || input.isEmpty())
			return null;
		else
			return (T) Enum.valueOf(this.enumClass, input);
	}

	public String format(T input) {
		return input.toString();
	}

}
