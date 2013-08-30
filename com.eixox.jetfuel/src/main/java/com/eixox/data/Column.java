package com.eixox.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {

	public ColumnType type() default ColumnType.Regular;

	public String dataName() default "";

	public boolean nullable() default true;
}
