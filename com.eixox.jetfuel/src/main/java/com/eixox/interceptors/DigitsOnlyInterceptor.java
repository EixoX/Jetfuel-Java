package com.eixox.interceptors;

import com.eixox.StringHelper;

public class DigitsOnlyInterceptor implements Interceptor {

	
	public final Object intercept(Object input) {
		if (input == null || !(input instanceof String))
			return input;
		else
			return StringHelper.digitsOnly((String) input);
	}

	private DigitsOnlyInterceptor() {
	}

	public static final DigitsOnlyInterceptor Instance = new DigitsOnlyInterceptor();

}
