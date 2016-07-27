package com.eixox;

import java.util.UUID;

import com.eixox.restrictions.MaxLength;
import com.eixox.restrictions.MinNumberExclusive;
import com.eixox.restrictions.Required;
import com.eixox.ui.Control;
import com.eixox.ui.ControlType;

public class UsecaseExample extends Usecase {

	@Required
	@MinNumberExclusive(0)
	@Control(ControlType.SINGLE_LINE)
	public long aLongProperty;

	@Required
	@MaxLength(255)
	@Control(ControlType.SINGLE_LINE)
	public String aStringProperty;

	@Override
	protected void executeFlow(UsecaseResult result) {
		result.resultType = UsecaseResultType.SUCCESS;
		result.result = UUID.randomUUID();
	}

}
