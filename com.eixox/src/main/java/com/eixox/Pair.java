package com.eixox;

public class Pair<TKey, TValue> {

	private final TKey _Key;
	private final TValue _Value;

	public Pair(TKey key, TValue value) {
		this._Key = key;
		this._Value = value;
	}

	public final TKey getKey() {
		return this._Key;
	}

	public final TValue getValue() {
		return this._Value;

	}
}
