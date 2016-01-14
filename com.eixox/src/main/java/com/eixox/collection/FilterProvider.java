package com.eixox.collection;

public interface FilterProvider<F> {
	boolean filter(F obj);
}
