package com.eixox.interceptors;

public interface EntityInterceptor<T> {

	public T intercept(T input);

}
