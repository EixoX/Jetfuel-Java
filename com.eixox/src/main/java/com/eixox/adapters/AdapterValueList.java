package com.eixox.adapters;

import com.eixox.PairList;

public class AdapterValueList extends PairList<ValueAdapter<?>, Object>
{
	private static final long serialVersionUID = 6641631442562230242L;

	public AdapterValueList() {
	}

	public AdapterValueList(int capacity) {
		super(capacity);
	}

	public void setValue(int ordinal, Object value) {
		super.get(ordinal).value = value;
	}

}
