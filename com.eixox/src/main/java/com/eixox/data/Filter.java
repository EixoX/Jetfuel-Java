package com.eixox.data;


public interface Filter {

	public DataAspect<?> getAspect();

	public FilterType getFilterType();
}
