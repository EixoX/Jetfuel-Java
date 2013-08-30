package com.eixox.interceptors;

public class UppercaseInterceptor implements Interceptor {

	@Override
	public Object intercept(Object input) {
		return input == null ? null : input.toString().toUpperCase();
	}

	private UppercaseInterceptor() {
	}

	public static final UppercaseInterceptor Instance = new UppercaseInterceptor();
}
