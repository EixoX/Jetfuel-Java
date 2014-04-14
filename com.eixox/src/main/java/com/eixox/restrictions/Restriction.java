package com.eixox.restrictions;

public interface Restriction {

	public boolean validate(Object input);

	public String getRestrictionMessageFor(Object input);

	public void assertValid(Object input) throws RestrictionException;

}
