package com.eixox.data;

import java.util.List;

public interface ColumnSchema<T extends Column> {

	public int getColumnCount();

	public T get(int ordinal);

	public T get(String name);

	public int getOrdinal(String name);

	public int getIdentityOrdinal();

	public List<T> getUniqueKeys();

	public List<T> getCompositeKeys();

	public String getTableName();
}
