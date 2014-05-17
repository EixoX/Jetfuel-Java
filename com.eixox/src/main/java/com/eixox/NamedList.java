package com.eixox;

import java.util.ArrayList;

public class NamedList<T extends Named> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8747069948520305496L;

	public NamedList() {
	}

	public NamedList(int capacity) {
		super(capacity);
	}

	public final int indexOf(String name) {
		if (name != null && !name.isEmpty()) {
			int s = this.size();
			for (int i = 0; i < s; i++)
				if (name.equalsIgnoreCase(super.get(i).getName()))
					return i;
		}
		return -1;
	}

	public final T get(String name) {
		return super.get(indexOf(name));
	}
}
