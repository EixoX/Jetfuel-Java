package com.eixox.interceptors;


public class CapitalizeInterceptor implements Interceptor {

	public final Object intercept(Object input) {
		if (input == null || !(input instanceof String))
			return input;
		else
		{
			String s = (String) input;
			if (s.isEmpty())
				return s;
			else if (s.length() < 3)
				return s.toLowerCase();
			else {
				return Character.toUpperCase(s.charAt(0)) + s.substring(1, s.length()).toLowerCase();
			}
		}
	}

	private CapitalizeInterceptor() {
	}

	public static final CapitalizeInterceptor Instance = new CapitalizeInterceptor();

}
