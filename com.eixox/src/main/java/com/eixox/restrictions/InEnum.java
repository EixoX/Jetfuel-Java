package com.eixox.restrictions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface InEnum {

	@SuppressWarnings("rawtypes")
	public Class<? extends Enum> value();
}
