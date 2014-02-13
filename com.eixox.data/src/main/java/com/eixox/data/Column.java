package com.eixox.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface Column {
	String name() default "";

	ColumnType type() default ColumnType.Normal;

	int offset() default -1;

	int length() default -1;

	int position() default -1;

	String locale() default "en";
	
	String encoding() default "utf-8";
	
	boolean nullable() default true;
}
