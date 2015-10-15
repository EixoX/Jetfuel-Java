package com.eixox.data;

public interface Storage {

	public DataSelect select(String name);

	public DataUpdate update(String name);

	public DataDelete delete(String name);

	public DataInsert insert(String name);
}
