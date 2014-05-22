package com.eixox.restrictions;

import java.util.LinkedList;

public class RestrictionList extends LinkedList<Restriction> implements Restriction {

	private static final long serialVersionUID = -4146631378228528782L;

	public boolean validate(Object input) {
		if (size() > 0)
			for (Restriction r : this)
				if (!r.validate(input))
					return false;

		return true;
	}

	public String getRestrictionMessageFor(Object input) {
		for (Restriction r : this) {
			String msg = r.getRestrictionMessageFor(input);
			if (msg != null)
				return msg;
		}
		return null;
	}

	public void assertValid(Object input) throws RestrictionException {
		String msg = getRestrictionMessageFor(input);
		if (msg != null)
			throw new RestrictionException(msg);
	}

}
