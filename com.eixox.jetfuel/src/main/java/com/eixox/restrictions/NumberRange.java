package com.eixox.restrictions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface NumberRange {
	double min();

	double max();
}
