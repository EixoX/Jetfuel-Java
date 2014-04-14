package com.eixox;

import java.util.ArrayList;

public class PairList<TKey, TValue> extends ArrayList<Pair<TKey, TValue>> {

	private static final long	serialVersionUID	= -2103168918011047330L;

	public PairList() {
	}

	public PairList(int capacity) {
		super(capacity);
	}

	public final Pair<TKey, TValue> add(TKey key, TValue value) {
		final Pair<TKey, TValue> pair = new Pair<TKey, TValue>(key, value);
		super.add(pair);
		return pair;
	}

	public final TKey getKey(int ordinal) {
		return super.get(ordinal).getKey();
	}

	public final TValue getValue(int ordinal) {
		return super.get(ordinal).getValue();
	}

}
