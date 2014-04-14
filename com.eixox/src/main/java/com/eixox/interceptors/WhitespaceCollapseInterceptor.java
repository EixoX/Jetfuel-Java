package com.eixox.interceptors;

import com.eixox.Strings;

public class WhitespaceCollapseInterceptor implements Interceptor {

	
	public Object intercept(Object input) {
		if (input == null)
			return null;
		else
			return Strings.whitespaceCollapse(input.toString());
	}

	private WhitespaceCollapseInterceptor() {
	}

	public static final WhitespaceCollapseInterceptor Instance = new WhitespaceCollapseInterceptor();

}
