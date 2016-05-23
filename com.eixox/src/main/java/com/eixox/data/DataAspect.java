package com.eixox.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class DataAspect<T, G extends DataAspectMember> implements ColumnSchema<G>, Iterable<G> {

	private final ArrayList<G> members;
	public final Class<T> dataType;
	public final String tableName;
	public final int identityOrdinal;
	public final ArrayList<G> uniqueKeys = new ArrayList<G>();
	public final ArrayList<G> compositeKeys = new ArrayList<G>();

	protected abstract G decorate(Field field);

	public DataAspect(Class<T> dataType, String tableName) {
		this.dataType = dataType;
		this.tableName = tableName;
		Field[] fields = this.dataType.getFields();
		this.members = new ArrayList<G>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			G member = decorate(fields[i]);
			if (member != null)
				this.members.add(member);
		}
		int idordinal = -1;
		int s = this.members.size();
		for (int i = 0; i < s; i++) {
			G member = this.members.get(i);
			switch (member.columnType) {
			case COMPOSITE_KEY:
				this.compositeKeys.add(member);
				break;
			case IDENTITY:
				if (idordinal >= 0)
					throw new RuntimeException("Please use only one identity per entity: " + this.dataType);
				else
					idordinal = i;
				break;
			case UNIQUE:
				this.uniqueKeys.add(member);
				break;
			default:
				break;
			}
		}
		this.identityOrdinal = idordinal;
	}

	public final T newInstance() {
		try {
			return (T) this.dataType.newInstance();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final int getColumnCount() {
		return this.members.size();
	}

	public final G get(int ordinal) {
		return this.members.get(ordinal);
	}

	public final G get(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException(name + " is not on data schema of " + this.dataType);
		else
			return this.members.get(ordinal);
	}

	public final int getOrdinal(String name) {
		int s = this.members.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(this.members.get(i).getName()))
				return i;
		return -1;
	}

	public final int getIdentityOrdinal() {
		return this.identityOrdinal;
	}

	public final List<G> getUniqueKeys() {
		return this.uniqueKeys;
	}

	public final List<G> getCompositeKeys() {
		return this.compositeKeys;
	}

	public final String getTableName() {
		return this.tableName;
	}

	public final Iterator<G> iterator() {
		return this.members.listIterator();
	}

}
