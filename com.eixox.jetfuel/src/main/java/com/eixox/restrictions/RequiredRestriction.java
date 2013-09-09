package com.eixox.restrictions;

import java.util.Date;

public class RequiredRestriction implements Restriction {

	public RequiredRestriction() {
	}

	public RequiredRestriction(Required required) 
	{
		//just complying to a constructor pattern.
	}

	
	public final boolean validate(Object input) {
		if (input == null)
			return false;
		else if (input instanceof String)
			return !"".equals(input);
		else if (input instanceof Number)
			return ((Number) input).intValue() != 0;
		else if (input instanceof Date)
			return ((Date) input).getTime() > 0;
		else
			return true;
	}

	
	public String getRestrictionMessageFor(Object input) {
		return validate(input) ? null : "required";
	}

	
	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);

	}

}
