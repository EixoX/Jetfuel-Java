package com.eixox.data.entities;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eixox.data.Aggregate;
import com.eixox.data.ColumnType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Persistent {
	public ColumnType value() default ColumnType.NORMAL;

	public String name() default "";

	public Class<?> adapterClass() default Object.class;

	public boolean nullable() default false;

	public Aggregate aggregate() default Aggregate.NONE;

	public boolean updatable() default true;
}
