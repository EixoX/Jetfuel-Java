package com.eixox.interceptors;

import com.eixox.StringHelper;

public class LetterOrDigitInterceptor implements Interceptor {

	
	public final Object intercept(Object input) {
		if (input == null || !(input instanceof String))
			return input;
		else
			return StringHelper.letterOrDigits((String) input);
	}

	private LetterOrDigitInterceptor() {
	}

	public static final LetterOrDigitInterceptor Instance = new LetterOrDigitInterceptor();

}
