package com.eixox.interceptors;

import com.eixox.StringHelper;

public class WhitespaceCollapseInterceptor implements Interceptor {

	@Override
	public Object intercept(Object input) {
		if (input == null)
			return null;
		else
			return StringHelper.whitespaceCollapse(input.toString());
	}

	private WhitespaceCollapseInterceptor() {
	}

	public static final WhitespaceCollapseInterceptor Instance = new WhitespaceCollapseInterceptor();

}
