package com.eixox.data.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eixox.data.ColumnType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Persistent {

	public String name() default "";

	public ColumnType type() default ColumnType.REGULAR;

	public boolean readonly() default false;

	public boolean nullable() default false;

}
