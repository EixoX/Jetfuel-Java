package com.eixox.reflection;

import java.util.ArrayList;

public class AspectMemberValues extends ArrayList<AspectMemberValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4791832281070367060L;

	public AspectMemberValues() {
	}

	public AspectMemberValues(int capacity) {
		super(capacity);
	}

	public AspectMemberValues add(Aspect aspect, int ordinal,
			Object value) {
		AspectMemberValue item = new AspectMemberValue(aspect,
				ordinal, value);
		super.add(item);
		return this;
	}
}
