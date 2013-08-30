package com.eixox.ui;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface UIControl {

	UIControlType Type();

	String Label() default "";

	String Hint() default "";

	String Placeholder() default "";

	Class<?> Source() default Object.class;
}
