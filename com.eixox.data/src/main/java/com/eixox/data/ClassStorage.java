package com.eixox.data;

import java.lang.reflect.Field;
import java.util.ArrayList;

// Description Here:
// _____________________________________________________
public abstract class ClassStorage {

	private final Class<?> dataType;
	private final ArrayList<ClassStorageColumn> columns;
	private final int identityOrdinal;
	private final ArrayList<Integer> uniqueOrdinals;
	private final ArrayList<Integer> primaryKeyOrdinals;
	private final String tableName;

	// Description Here:
	// _____________________________________________________
	public ClassStorage(Class<?> dataType, String tableName) {
		this.dataType = dataType;
		Field[] fields = dataType.getDeclaredFields();
		this.columns = new ArrayList<ClassStorageColumn>(fields.length);
		this.uniqueOrdinals = new ArrayList<Integer>();
		this.primaryKeyOrdinals = new ArrayList<Integer>();
		this.tableName = tableName == null || tableName.isEmpty() ? dataType.getName() : tableName;

		for (int i = 0; i < fields.length; i++) {
			ClassStorageColumn mapping = map(fields[i]);
			if (mapping != null) {
				fields[i].setAccessible(true);
				this.columns.add(mapping);
			}
		}

		int idOrdinal = -1;
		int l = columns.size();

		for (int i = 0; i < l; i++) {
			switch (columns.get(i).getColumnType()) {
			case Identity:
				if (idOrdinal < 0)
					idOrdinal = i;
				else
					throw new RuntimeException("Please make sure that " + dataType + " has only one identity.");
				break;
			case Unique:
				this.uniqueOrdinals.add(i);
				break;
			case PrimaryKey:
				this.primaryKeyOrdinals.add(i);
				break;
			default:
				break;

			}
		}
		this.identityOrdinal = idOrdinal;
	}

	// Description Here:
	// _____________________________________________________
	public final Class<?> getDataType() {
		return this.dataType;
	}

	// Description Here:
	// _____________________________________________________
	public final String getTableName() {
		return this.tableName;
	}

	// Description Here:
	// _____________________________________________________
	public final int getIdentityOrdinal() {
		return this.identityOrdinal;
	}

	// Description Here:
	// _____________________________________________________
	public final boolean hasIdentity() {
		return this.identityOrdinal >= 0;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorageColumn getIdentity() {
		return this.identityOrdinal >= 0 ? this.columns.get(this.identityOrdinal) : null;
	}

	// Description Here:
	// _____________________________________________________
	public final String getIdentityName() {
		return this.identityOrdinal >= 0 ? this.columns.get(this.identityOrdinal).getColumnName() : null;
	}

	// Description Here:
	// _____________________________________________________
	protected abstract ClassStorageColumn map(Field field);

	// Description Here:
	// _____________________________________________________
	public final int getOrdinal(String name) {
		if (name != null && !name.isEmpty()) {
			int l = columns.size();
			for (int i = 0; i < l; i++)
				if (name.equalsIgnoreCase(columns.get(i).getName()))
					return i;
		}
		return -1;
	}

	// Description Here:
	// _____________________________________________________
	public final int getColumnCount() {
		return this.columns.size();
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorageColumn getColumn(int ordinal) {
		return this.columns.get(ordinal);
	}

	public abstract ClassDelete delete();

	public abstract ClassUpdate update();

	public abstract ClassInsert insert();

	public abstract <T> ClassSelect<T> select();
}
