package com.eixox;

public interface Visitor<T> {

	public boolean visit(T item);
}
