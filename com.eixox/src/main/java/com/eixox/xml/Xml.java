package com.eixox.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.TYPE })
public @interface Xml {

	public XmlValueFormat valueFormat() default XmlValueFormat.TEXT;

	public String name() default "";
}
