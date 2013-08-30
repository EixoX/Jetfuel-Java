package com.eixox.adapters;

public interface ValueAdapter<T> {
	public T adapt(Object input);

	public T parse(String input);

	public String format(T input);
}
