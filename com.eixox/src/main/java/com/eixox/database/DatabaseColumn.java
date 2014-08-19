package com.eixox.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eixox.data.ColumnType;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface DatabaseColumn {

	public ColumnType type() default ColumnType.REGULAR;

	public String columnName() default "";

	public boolean nullable() default false;

	public String caption() default "";
	
	public boolean readonly() default false;

}
