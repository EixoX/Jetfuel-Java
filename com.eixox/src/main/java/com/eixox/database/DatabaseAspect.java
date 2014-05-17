package com.eixox.database;

import java.util.ArrayList;
import java.util.HashMap;

import com.eixox.data.DataAspect;
import com.eixox.data.Filter;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterTerm;
import com.eixox.reflection.AspectMember;

public final class DatabaseAspect extends DataAspect<DatabaseAspectMember> {

	private final int										identityOrdinal;
	private final int[]										uniqueOrdinals;
	private final int[]										primaryKeys;

	private static final HashMap<Class<?>, DatabaseAspect>	instances	= new HashMap<Class<?>, DatabaseAspect>();

	public static synchronized final DatabaseAspect getInstance(Class<?> claz) {
		DatabaseAspect da = instances.get(claz);
		if (da == null) {
			da = new DatabaseAspect(claz);
			instances.put(claz, da);
		}
		return da;
	}

	private final static String getTableName(Class<?> dataType) {
		DatabaseTable tb = dataType.getAnnotation(DatabaseTable.class);
		if (tb == null)
			throw new RuntimeException("Please annotate " + dataType + " as @Table");
		else
			return tb.dataName();
	}

	private DatabaseAspect(Class<?> dataType) {
		super(dataType, getTableName(dataType));

		if (super.getCount() == 0) {
			throw new RuntimeException("Please annotate " + dataType + " with at least one @Column");
		}

		int idO = -1;
		ArrayList<Integer> uos = new ArrayList<Integer>(2);
		ArrayList<Integer> pks = new ArrayList<Integer>(4);
		int s = super.getCount();
		for (int i = 0; i < s; i++) {
			DatabaseAspectMember column = super.get(i);
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

	public final DatabaseAspectMember getIdentity() {
		return this.identityOrdinal < 0 ? null : super.get(this.identityOrdinal);
	}

	public final Object getIdentityValue(Object entity) {
		return this.get(this.identityOrdinal).getValue(entity);
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

	public final DatabaseAspectMember getUniqueColumn(int index) {
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

	public final DatabaseAspectMember getPrimaryKey(int index) {
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

	@Override
	protected final DatabaseAspectMember decorate(AspectMember member) {
		DatabaseColumn column = member.getAnnotation(DatabaseColumn.class);
		if (column == null)
			return null;
		else
			return new DatabaseAspectMember(member, column);
	}

	public final Filter createPrimaryKeyFilter(Object entity) {
		if (this.primaryKeys == null || this.primaryKeys.length == 0)
			return null;
		else if (this.primaryKeys.length == 1)
			return new FilterTerm(this, primaryKeys[0], FilterComparison.EQUAL_TO, getValue(entity, this.primaryKeys[0]));
		else {
			FilterExpression exp = new FilterExpression(new FilterTerm(this, primaryKeys[0], FilterComparison.EQUAL_TO, getValue(entity, this.primaryKeys[0])));
			for (int i = 1; i < this.primaryKeys.length; i++)
				exp.and(this.primaryKeys[i], getValue(entity, this.primaryKeys[i]));
			return exp;
		}
	}

}
