package com.eixox.data;

import java.util.ArrayList;
import java.util.HashMap;

import com.eixox.Strings;
import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public final class DataAspect extends AbstractAspect<DataAspectMember> {

	private final String								dataName;
	private final int									identityOrdinal;
	private final int[]									uniqueOrdinals;
	private final int[]									primaryKeys;

	private static final HashMap<Class<?>, DataAspect>	instances	= new HashMap<Class<?>, DataAspect>();

	public static synchronized final DataAspect getInstance(Class<?> claz) {
		DataAspect da = instances.get(claz);
		if (da == null) {
			da = new DataAspect(claz);
			instances.put(claz, da);
		}
		return da;
	}

	private DataAspect(Class<?> dataType) {
		super(dataType);

		if (super.getCount() == 0) {
			throw new RuntimeException("Please annotate " + dataType + " with at least one @Column");
		}

		Table tb = dataType.getAnnotation(Table.class);
		if (tb == null)
			throw new RuntimeException("Please annotate " + dataType + " as @Table");
		else
			this.dataName = Strings.isNullOrEmptyAlternate(tb.dataName(), dataType.getSimpleName());

		int idO = -1;
		ArrayList<Integer> uos = new ArrayList<Integer>(2);
		ArrayList<Integer> pks = new ArrayList<Integer>(4);
		int s = super.getCount();
		for (int i = 0; i < s; i++) {
			DataAspectMember column = super.get(i);
			switch (column.getColumnType()) {
				case IDENTITY:
					if (idO < 0)
						idO = i;
					else
						throw new RuntimeException("Only one identity is allowed per class: " + dataType);
					break;
				case PRIMARY_KEY:
					pks.add(i);
					break;
				case UNIQUE:
					uos.add(i);
					break;
				default:
					break;
			}
		}

		this.identityOrdinal = idO;
		this.primaryKeys = new int[pks.size()];
		for (int i = 0; i < this.primaryKeys.length; i++)
			this.primaryKeys[i] = pks.get(i);
		this.uniqueOrdinals = new int[uos.size()];
		for (int i = 0; i < this.uniqueOrdinals.length; i++)
			this.uniqueOrdinals[i] = uos.get(i);
	}

	public final int getIdentityOrdinal() {
		return this.identityOrdinal;
	}

	public final boolean hasIdentity() {
		return this.identityOrdinal >= 0;
	}

	public final DataAspectMember getIdentity() {
		return this.identityOrdinal < 0 ? null : super.get(this.identityOrdinal);
	}

	public final int[] getUniqueOrdinals() {
		return this.uniqueOrdinals;
	}

	public final boolean hasUniqueOrdinals() {
		return this.uniqueOrdinals.length > 0;
	}

	public final int getUniqueColumnCount() {
		return this.uniqueOrdinals.length;
	}

	public final int getUniqueColumnOrdinal(int index) {
		return this.uniqueOrdinals[index];
	}

	public final DataAspectMember getUniqueColumn(int index) {
		return super.get(this.uniqueOrdinals[index]);
	}

	public final int[] getPrimaryKeyOrdinals() {
		return this.primaryKeys;
	}

	public final boolean hasPrimaryKeys() {
		return this.primaryKeys.length > 0;
	}

	public final int getPrimaryKeyCount() {
		return this.primaryKeys.length;
	}

	public final int getPrimaryKeyOrdinal(int index) {
		return this.primaryKeys[index];
	}

	public final DataAspectMember getPrimaryKey(int index) {
		return super.get(this.primaryKeys[index]);
	}

	public final boolean isPrimaryKey(int ordinal) {
		for (int i = 0; i < primaryKeys.length; i++)
			if (ordinal == primaryKeys[i])
				return true;

		return false;
	}

	public final int getColumnOrdinal(String name) {
		int s = super.getCount();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(super.get(i).getDataName()))
				return i;
		return -1;
	}

	public final int getColumnOrdinalOrException(String name) {
		int ordinal = getColumnOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException("No column data name mapped to " + name + " on " + super.getDataType());
		else
			return ordinal;
	}

	public final String getColumnName(int ordinal) {
		return super.get(ordinal).getDataName();
	}

	@Override
	protected final DataAspectMember decorate(AspectMember member) {
		Column column = member.getAnnotation(Column.class);
		if (column == null)
			return null;
		else
			return new DataAspectMember(member, column);
	}

	public final String getDataName() {
		return this.dataName;
	}

}
