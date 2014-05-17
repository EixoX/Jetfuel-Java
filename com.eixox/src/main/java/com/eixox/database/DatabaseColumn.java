package com.eixox.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface DatabaseColumn {

	public DatabaseColumnType type() default DatabaseColumnType.REGULAR;

	public String dataName() default "";

	public boolean nullable() default false;

	public String caption() default "";

}
