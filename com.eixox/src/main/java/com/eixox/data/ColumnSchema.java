package com.eixox.data;

public interface ColumnSchema {

	public int getCount();

	public Column getColumn(int ordinal);

	public Column getColumn(String name);

	public int getOrdinal(String name);

	public int getOrdinalOrException(String name);

	public int getIdentityOrdinal();

	public int[] getUniqueOrdinals();

	public int[] getCompositeKeyOrdinals();

	public boolean hasIdentity();

	public boolean hasUniques();

	public boolean hasCompositeKey();

	public String getColumnName(int ordinal);

}
