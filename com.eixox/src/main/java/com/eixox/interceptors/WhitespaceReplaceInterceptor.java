package com.eixox.interceptors;

import com.eixox.Strings;

public class WhitespaceReplaceInterceptor implements Interceptor {

	
	public final Object intercept(Object input) {
		if (input == null || !(input instanceof String))
			return null;
		else
			return Strings.whitespaceReplace((String) input);
	}

	private WhitespaceReplaceInterceptor() {
	}

	public static final WhitespaceReplaceInterceptor Instance = new WhitespaceReplaceInterceptor();

}
