package com.eixox;

import java.util.Date;

import com.eixox.restrictions.MinLength;
import com.eixox.restrictions.Required;

public class UsecaseInherited extends UsecaseExample {

	@Required
	@MinLength(3)
	public String anotherProperty;

	@Required
	public Date andAThird;
}
