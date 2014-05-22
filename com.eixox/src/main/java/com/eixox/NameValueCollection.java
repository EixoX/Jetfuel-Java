package com.eixox;

public class NameValueCollection<TValue> extends PairList<String, TValue> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4453202385148982573L;

	public int indexOf(String name) {
		int l = super.size();
		for (int i = 0; i < l; i++)
			if (name.equalsIgnoreCase(super.getKey(i)))
				return i;
		return -1;
	}

	public TValue get(String name) {
		int ordinal = indexOf(name);
		return ordinal >= 0 ? super.getValue(ordinal) : null;
	}

}
