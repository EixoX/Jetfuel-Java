package com.eixox.interceptors;

import java.lang.annotation.Annotation;

public class InterceptorBuilder {

	public Interceptor build(Annotation annotation) {

		if (annotation instanceof DigitsOnly)
			return DigitsOnlyInterceptor.Instance;

		if (annotation instanceof LetterOrDigit)
			return LetterOrDigitInterceptor.Instance;

		if (annotation instanceof Lowercase)
			return LowercaseInterceptor.Instance;

		if (annotation instanceof Uppercase)
			return UppercaseInterceptor.Instance;

		if (annotation instanceof WhitespaceCollapse)
			return WhitespaceCollapseInterceptor.Instance;

		if (annotation instanceof WhitespaceReplace)
			return WhitespaceReplaceInterceptor.Instance;
		
		if(annotation instanceof Capitalize)
			return CapitalizeInterceptor.Instance;

		return null;
	}

	public static final InterceptorBuilder defaultInstance = new InterceptorBuilder();
}
