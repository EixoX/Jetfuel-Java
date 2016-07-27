package com.eixox.cassandra;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eixox.data.ColumnType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CassandraColumn {

	public ColumnType columnType() default ColumnType.REGULAR;

	public String name() default "";
}
