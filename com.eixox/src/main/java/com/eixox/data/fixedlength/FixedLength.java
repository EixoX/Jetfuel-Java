package com.eixox.data.fixedlength;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.eixox.data.adapters.StringAdapter;
import com.eixox.data.adapters.ValueAdapter;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface FixedLength {

	public int start();

	public int end();

	public Class<? extends ValueAdapter<?>> adapter() default StringAdapter.class;
}
