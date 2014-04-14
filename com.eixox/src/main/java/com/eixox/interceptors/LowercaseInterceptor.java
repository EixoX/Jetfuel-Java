package com.eixox.interceptors;

public class LowercaseInterceptor implements Interceptor {

	
	public Object intercept(Object input) {
		return input == null ? null : input.toString().toLowerCase();
	}

	private LowercaseInterceptor() {
	}

	public static final LowercaseInterceptor Instance = new LowercaseInterceptor();

}
