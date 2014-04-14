package com.eixox.restrictions;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MinNumberExclusive {

	double value();
}
