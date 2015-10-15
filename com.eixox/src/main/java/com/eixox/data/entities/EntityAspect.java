package com.eixox.data.entities;

import java.util.ArrayList;
import java.util.HashMap;

import com.eixox.Arrays;
import com.eixox.Strings;
import com.eixox.data.FilterExpression;
import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class EntityAspect extends AbstractAspect<EntityAspectMember> {

	public final String tableName;
	public final int identityOrdinal;
	public final int[] uniqueOrdinals;
	public final int[] compositeKeyOrdinals;

	protected EntityAspect(Class<?> dataType) {
		super(dataType);

		Persistent ps = dataType.getAnnotation(Persistent.class);
		this.tableName = ps == null ? dataType.getName() : Strings.isNullOrEmptyAlternate(ps.name(), dataType.getName());

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

	public final FilterExpression buildCompositeKeyFilter(Object entity) {
		if (this.compositeKeyOrdinals == null || this.compositeKeyOrdinals.length == 0)
			return null;

		EntityAspectMember member = get(this.compositeKeyOrdinals[0]);
		FilterExpression exp = new FilterExpression(member.columnName, member.getValue(entity));
		for (int i = 1; i < this.compositeKeyOrdinals.length; i++) {
			member = get(this.compositeKeyOrdinals[i]);
			exp.and(member.columnName, member.getValue(entity));
		}
		return exp;
	}

	public final boolean hasIdentity() {
		return this.identityOrdinal >= 0;
	}

	public final EntityAspectMember getIdentity() {
		return this.identityOrdinal >= 0 ? super.get(this.identityOrdinal) : null;
	}

	public final boolean hasUniques() {
		return this.uniqueOrdinals != null && this.uniqueOrdinals.length > 0;
	}

	public final String getUniqueColumnName(int index) {
		if (index < 0 || index >= this.uniqueOrdinals.length)
			return null;
		return super.get(this.uniqueOrdinals[index]).columnName;
	}

	public final boolean hasCompositeKey() {
		return this.compositeKeyOrdinals != null && this.compositeKeyOrdinals.length > 0;
	}

	public final boolean isCompositeKeyMember(int ordinal) {
		for (int i = 0; i < this.compositeKeyOrdinals.length; i++)
			if (ordinal == this.compositeKeyOrdinals[i])
				return true;

		return false;
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

	@Override
	protected EntityAspectMember decorate(AspectMember member) {
		Persistent ps = member.getAnnotation(Persistent.class);
		if (ps != null) {
			return new EntityAspectMember(member, ps.type(), ps.name(), ps.nullable(), ps.readonly());
		} else
			return null;
	}

	public static HashMap<Class<?>, EntityAspect> DEFAULT_INSTANCES = new HashMap<Class<?>, EntityAspect>();

	public static synchronized final EntityAspect getDefaultInstance(Class<?> claz) {
		EntityAspect aspect = DEFAULT_INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new EntityAspect(claz);
			DEFAULT_INSTANCES.put(claz, aspect);
		}
		return aspect;
	}

}
