package com.eixox.data;

public interface Adapter<TFrom, TTo> {

	public TTo adapt(TFrom source);
}
