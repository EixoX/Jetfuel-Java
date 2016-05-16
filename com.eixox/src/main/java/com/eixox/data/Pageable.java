package com.eixox.data;

public interface Pageable<T> {

	public T page(int pageSize, int pageOrdinal);

	public T limit(int pageSize);

	public T offset(int offset);
}
