package com.eixox;

import java.util.UUID;

import com.eixox.globalization.Culture;
import com.eixox.restrictions.MaxLength;
import com.eixox.restrictions.MinNumberExclusive;
import com.eixox.restrictions.Required;

public class UsecaseExample extends Usecase {

	public UsecaseExample(Culture culture) {
		super(culture);
	}

	public UsecaseExample() {
	}

	@Required
	@MinNumberExclusive(0)
	public long aLongProperty;

	@Required
	@MaxLength(255)
	public String aStringProperty;

	@Override
	protected void executeFlow(UsecaseResult result) {
		result.resultType = UsecaseResultType.SUCCESS;
		result.result = UUID.randomUUID();
	}

}
