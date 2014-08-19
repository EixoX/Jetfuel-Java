package com.eixox.data.entities;

import java.util.ArrayList;

import com.eixox.Arrays;
import com.eixox.reflection.AbstractAspect;

public abstract class EntityAspect extends AbstractAspect<EntityAspectMember> {

	public final String tableName;
	public final int identityOrdinal;
	public final int[] uniqueOrdinals;
	public final int[] compositeKeyOrdinals;

	protected EntityAspect(Class<?> dataType, String tableName) {
		super(dataType);
		this.tableName = tableName == null || tableName.isEmpty() ?
				dataType.getSimpleName() :
				tableName;

		int iord = -1;
		ArrayList<Integer> uOrdinals = new ArrayList<Integer>();
		ArrayList<Integer> cOrdinals = new ArrayList<Integer>();
		int imax = getCount();

		for (int i = 0; i < imax; i++) {
			switch (get(i).columntType) {
				case COMPOSITE_KEY:
					cOrdinals.add(i);
					break;
				case IDENTITY:
					if (iord < 0)
						iord = i;
					else
						throw new RuntimeException(getDataType() + " has multiple data identities defined. Please remove some.");
					break;
				case UNIQUE:
					uOrdinals.add(i);
					break;
				default:
					break;
			}
		}

		this.identityOrdinal = iord;
		this.uniqueOrdinals = Arrays.toInt(uOrdinals);
		this.compositeKeyOrdinals = Arrays.toInt(cOrdinals);
	}

	public final EntityFilterExpression buildCompositeKeyFilter(Object entity) {
		if (this.compositeKeyOrdinals == null || this.compositeKeyOrdinals.length == 0)
			return null;

		EntityFilterExpression exp = new EntityFilterExpression(this, this.compositeKeyOrdinals[0], get(this.compositeKeyOrdinals[0]).getValue(entity));
		for (int i = 1; i < this.compositeKeyOrdinals.length; i++)
			exp.and(this.compositeKeyOrdinals[i], get(this.compositeKeyOrdinals[i]).getValue(entity));
		return exp;
	}

	public final boolean hasIdentity() {
		return this.identityOrdinal >= 0;
	}

	public final boolean hasUniques() {
		return this.uniqueOrdinals != null && this.uniqueOrdinals.length > 0;
	}

	public final boolean hasCompositeKey() {
		return this.compositeKeyOrdinals != null && this.compositeKeyOrdinals.length > 0;
	}

	public final String getColumnName(int ordinal) {
		return super.get(ordinal).columnName;
	}

	public final int getColumnOrdinal(String columnName) {
		int size = super.getCount();
		for (int i = 0; i < size; i++)
			if (columnName.equalsIgnoreCase(get(i).columnName))
				return i;
		return -1;
	}

}
