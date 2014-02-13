package com.eixox.data;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Description Here:
// _____________________________________________________
public abstract class ClassStorage<T> {

	private final Class<T> dataType;
	private final ClassStorageColumn[] columns;
	private final int identityOrdinal;
	private final int[] uniqueOrdinals;
	private final int[] primaryKeyOrdinals;
	private final String tableName;

	// Description Here:
	// _____________________________________________________
	public ClassStorage(Class<T> dataType) {
		Table tableAnnotation = dataType.getAnnotation(Table.class);
		if (tableAnnotation == null)
			throw new RuntimeException(dataType + " is not annotated as Table");

		this.dataType = dataType;
		this.tableName = tableAnnotation.name() == null || tableAnnotation.name().isEmpty() ? dataType.getName() : tableAnnotation.name();

		final Field[] fields = dataType.getDeclaredFields();
		final ArrayList<ClassStorageColumn> cols = new ArrayList<ClassStorageColumn>(fields.length);

		for (int i = 0; i < fields.length; i++) {
			Column columnAnnotation = fields[i].getAnnotation(Column.class);
			if (columnAnnotation != null) {
				fields[i].setAccessible(true);
				cols.add(new ClassStorageColumn(fields[i], columnAnnotation));
			}
		}

		if (cols.size() == 0)
			throw new RuntimeException(dataType + " has no columns annotated");

		int idOrdinal = -1;
		final int l = cols.size();
		final ArrayList<Integer> uos = new ArrayList<Integer>();
		final ArrayList<Integer> pkos = new ArrayList<Integer>();
		this.columns = new ClassStorageColumn[l];

		for (int i = 0; i < l; i++) {
			this.columns[i] = cols.get(i);
			switch (cols.get(i).getColumnType()) {
			case Identity:
				if (idOrdinal < 0)
					idOrdinal = i;
				else
					throw new RuntimeException("Please make sure that " + dataType + " has only one identity.");
				break;
			case Unique:
				uos.add(i);
				break;
			case PrimaryKey:
				pkos.add(i);
				break;
			default:
				break;

			}
		}

		this.identityOrdinal = idOrdinal;

		this.uniqueOrdinals = new int[uos.size()];
		for (int i = 0; i < this.uniqueOrdinals.length; i++)
			this.uniqueOrdinals[i] = uos.get(i);

		this.primaryKeyOrdinals = new int[pkos.size()];
		for (int i = 0; i < this.primaryKeyOrdinals.length; i++)
			this.primaryKeyOrdinals[i] = pkos.get(i);
	}

	// Description Here:
	// _____________________________________________________
	public final Class<T> getDataType() {
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
		return this.identityOrdinal >= 0 ? this.columns[this.identityOrdinal] : null;
	}

	// Description Here:
	// _____________________________________________________
	public final String getIdentityName() {
		return this.identityOrdinal >= 0 ? this.columns[this.identityOrdinal].getColumnName() : null;
	}

	// Gets the column ordinal by name.
	public synchronized final int getOrdinal(String name) {
		if (name != null && !name.isEmpty()) {
			final int l = columns.length;
			for (int i = 0; i < l; i++)
				if (name.equalsIgnoreCase(columns[i].getName()))
					return i;
		}
		return -1;
	}

	// Gets the column ordinal by name.
	public synchronized final int getOrdinalOrException(String name) {
		if (name != null && !name.isEmpty()) {
			final int l = columns.length;
			for (int i = 0; i < l; i++)
				if (name.equalsIgnoreCase(columns[i].getName()))
					return i;
		}
		throw new RuntimeException("Unknown column " + name);
	}

	// Gets the column count
	public final int size() {
		return this.columns.length;
	}

	// Gets the column (or member) by ordinal.
	public final ClassStorageColumn get(int ordinal) {
		return this.columns[ordinal];
	}

	// Gets the column (or member) by name
	public final ClassStorageColumn get(String name) {
		return this.columns[this.getOrdinalOrException(name)];
	}

	// Gets the column name by a given ordinal
	public final String getColumnName(int ordinal) {
		return this.columns[ordinal].getColumnName();
	}

	// Gets the value of a member (or column) by ordinal
	public final Object getValue(Object entity, int ordinal) {
		return this.columns[ordinal].getValue(entity);
	}

	// Sets the value of a member (or column) by ordinal
	public final void setValue(Object entity, int ordinal, Object value) {
		this.columns[ordinal].setValue(entity, value);
	}

	// Gets the value of a member (or column) by name
	public final Object getValue(Object entity, String name) {
		return this.columns[this.getOrdinalOrException(name)].getValue(entity);
	}

	// Sets the value of a member (or column) by name
	public final void setValue(Object entity, String name, Object value) {
		this.columns[getOrdinalOrException(name)].setValue(entity, value);
	}

	// Creates a new storage select command
	public final ClassSelect<T> select() {
		return new ClassSelect<T>(this);
	}

	// Selects members to a list
	public abstract List<T> select(Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	// Selects members to a list
	public abstract List<Object> selectMember(int memberOrdinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	// Executes a count with the given filter
	public abstract long selectCount(Filter filter);

	// Executes a count with no filter
	public final long selectCount() {
		return selectCount(null);
	}

	// Executes an exists command with a given filter.
	public abstract boolean selectExists(Filter filter);

	// Reads one element with a given filter
	public abstract T read(Filter filter);

	// Reads one element with a given ordinal and value
	public final T read(int ordinal, Object value) {
		return read(new FilterTerm(this, ordinal, value));
	}

	// Reads one element with a given ordinal and values
	public final T read(String name, Object value) {
		return read(new FilterTerm(this, name, value));
	}

	// Reads only the given member by a given filter
	public abstract Object readMember(int ordinal, Filter filter);

	// Reads only the given member by a given filter
	public final Object readMember(String name, Filter filter) {
		return readMember(getOrdinalOrException(name), filter);
	}

	// _ REGION: DELETE
	// _________________________________________________________________________

	// Creates a new storage delete command.
	public final ClassDelete delete() {
		return new ClassDelete(this);
	}

	// Deletes from the storage by a given filter.
	public abstract long deleteWhere(Filter filter);

	// Deletes an entity based on the identity value, unique column or primary
	// key composition. Returns either the number of items deleted or -1 if no
	// delete method is found.
	public synchronized final long delete(Object item) {
		// delete by identity
		if (this.identityOrdinal >= 0) {
			Object identityValue = this.columns[identityOrdinal].getValue(item);
			if (!ValueHelper.isNullOrEmpty(identityValue))
				return deleteWhere(new FilterTerm(this, this.identityOrdinal, identityValue));
		}
		// delete by unique column
		for (Integer unique : this.uniqueOrdinals) {
			Object uniqueValue = this.columns[unique].getValue(item);
			if (!ValueHelper.isNullOrEmpty(uniqueValue))
				return deleteWhere(new FilterTerm(this, unique, uniqueValue));
		}
		// delete by primary keys
		if (this.primaryKeyOrdinals.length > 0) {
			ClassDelete pkDelete = delete();
			for (Integer pkOrdinal : this.primaryKeyOrdinals)
				pkDelete.and(pkOrdinal, this.columns[pkOrdinal].getValue(item));
			return pkDelete.execute();
		}
		throw new RuntimeException("We found no way to delete " + item);
	}

	// _ REGION: UPDATE
	// _________________________________________________________________________

	// Create an update command for the storage.
	public final ClassUpdate update() {
		return new ClassUpdate(this);
	}

	// Updates the storage with given values and filter.
	public abstract long updateWhere(ArrayList<Integer> ordinals, ArrayList<Object> values, Filter where);

	// Updates an entity based on the identity value, unique column or primary
	// key composition. Returns either the number of rows updated or -1 if no
	// delete method is found.
	public synchronized final long update(Object entity) {
		// update by identity
		if (this.identityOrdinal >= 0) {
			final Object identityValue = this.columns[identityOrdinal].getValue(entity);
			if (!ValueHelper.isNullOrEmpty(identityValue))
				return updateByMember(entity, this.identityOrdinal);
		}
		// update by unique column
		for (Integer uniqueId : this.uniqueOrdinals) {
			Object uniqueValue = this.columns[uniqueId].getValue(entity);
			if (!ValueHelper.isNullOrEmpty(uniqueValue)) {
				return updateByMember(entity, uniqueId);
			}
		}
		// update by primary keys
		if (this.primaryKeyOrdinals.length > 0) {
			return updateByMembers(entity, this.primaryKeyOrdinals);
		} else
			throw new RuntimeException("We found no way to update " + entity);

	}

	// Updates an entity based on the identity. Returns the number of entities
	// affected.
	public synchronized final long updateByIdentity(Object entity) {
		if (this.identityOrdinal >= 0) {
			final Object identityValue = this.columns[identityOrdinal].getValue(entity);
			if (!ValueHelper.isNullOrEmpty(identityValue))
				return updateByMember(entity, this.identityOrdinal);
			else
				throw new RuntimeException("The identity value for " + this.dataType + " is null or empty: " + identityValue);
		} else
			throw new RuntimeException("No identity defined for " + this.dataType);
	}

	// Updates an entity based on the filter member ordinal. Returns the number
	// of entities affected.
	public synchronized final long updateByMember(Object entity, int ordinal) {
		final int columnCount = this.columns.length;
		if (ordinal < 0 || ordinal >= columnCount)
			throw new RuntimeException("Unkown column ordinal " + ordinal + ", expected [0, " + columnCount + "[");

		final ClassUpdate upd = update();
		for (int i = 0; i < columnCount; i++) {
			if (i != identityOrdinal && i != ordinal)
				upd.set(i, this.columns[i].getValue(entity));
		}
		return upd.where(ordinal, this.columns[ordinal].getValue(entity)).execute();
	}

	// Updates an entity based on the filter member name. Returns the number of
	// entities affected.
	public synchronized final long updateByMember(Object entity, String name) {
		return updateByMember(entity, getOrdinalOrException(name));
	}

	// Updates an entity based on the member ordinal passed on the function.
	// Returns the number of entities affected.
	public synchronized final long updateByMembers(Object entity, int... ordinals) {
		final int columnCount = this.columns.length;
		final ClassUpdate update = update();
		Object value;
		for (int i = 0; i < columnCount; i++) {
			value = this.columns[i].getValue(entity);
			boolean isWhere = false;
			for (int j = 0; j < ordinals.length; j++) {
				if (i == ordinals[j]) {
					isWhere = true;
					break;
				}
			}
			if (isWhere)
				update.and(i, value);
			else if (i != identityOrdinal)
				update.set(i, value);
		}
		return update.execute();
	}

	// Updates an entity based on the member names passed on the function.
	// Returns the number of entities affected.
	public synchronized final long updateByMembers(Object entity, String... names) {
		final int[] ordinals = new int[names.length];
		for (int i = 0; i < names.length; i++)
			ordinals[i] = this.getOrdinalOrException(names[i]);
		return updateByMembers(entity, ordinals);
	}

	// __ REGION INSERT ____

	// Creates a new storage insert command.
	public final ClassInsert insert() {
		return new ClassInsert(this);
	}

	// Inserts the items on the storage.
	public abstract long insert(ArrayList<Integer> ordinals, ArrayList<Object> values);

	// Inserts the items on the storage and scopes the newly created identity.
	public abstract Object insertAndScopeIdentity(ArrayList<Integer> ordinals, ArrayList<Object> values);

	// Inserts an entity
	public synchronized final boolean insert(Object entity) {
		final ClassInsert insert = insert();
		final int l = this.columns.length;
		for (int i = 0; i < l; i++)
			if (i != this.identityOrdinal)
				insert.add(i, this.columns[i].getValue(entity));

		if (this.identityOrdinal >= 0) {
			final Object identityValue = insert.executeScopeIdentity();
			if (!ValueHelper.isNullOrEmpty(identityValue)) {
				this.columns[identityOrdinal].setValue(entity, identityValue);
				return true;
			} else
				return false;
		} else {
			return insert.execute() > 0;
		}
	}

	public synchronized final boolean existsWithMember(Object entity, int memberOrdinal) {
		if (memberOrdinal < 0 || memberOrdinal > this.columns.length)
			throw new RuntimeException("No such ordinal " + memberOrdinal + ", expected [0, " + this.columns.length + "[");
		return select().where(memberOrdinal, getValue(entity, memberOrdinal)).exists();

	}

	// Checks if a given object exists with the member (or column name).
	public final boolean existsWithMember(Object entity, String name) {
		return existsWithMember(entity, this.getOrdinalOrException(name));
	}

	// Checks if a given object exists with the members (or columns names)
	// provided.
	public synchronized final boolean existsWithMembers(Object entity, int... memberOrdinals) {
		ClassSelect<T> select = select();
		for (int i = 0; i < memberOrdinals.length; i++)
			if (memberOrdinals[i] < 0 || memberOrdinals[i] > this.columns.length)
				throw new RuntimeException("No such ordinal " + memberOrdinals[i] + ", expected [0, " + this.columns.length + "[");
			else
				select.and(i, getValue(entity, i));
		return select.exists();

	}

	// Saves an entity by the value of its unique members. If one exists, it's
	// an update, otherwise an insert.
	public synchronized final long saveByUniqueMembers(Object entity) {
		if (this.uniqueOrdinals.length == 0)
			throw new RuntimeException("No unique members to save " + entity);
		for (int i = 0; i < uniqueOrdinals.length; i++) {
			Object uniqueValue = getValue(entity, this.uniqueOrdinals[i]);
			if (!ValueHelper.isNullOrEmpty(uniqueValue)) {
				if (existsWithMember(entity, this.uniqueOrdinals[i]))
					return updateByMember(entity, uniqueOrdinals[i]);
			}
		}
		return insert(entity) ? 1 : 0;
	}

	// Saves an entity by the values of its primary keys. If one exists, it's
	// an update, otherwise an insert.
	public synchronized final long saveByPrimaryKeys(Object entity) {
		final int pkl = this.primaryKeyOrdinals.length;
		if (pkl == 0)
			throw new RuntimeException("There are no primary keys to save " + entity);

		ClassSelect<?> slct = select();
		for (int i = 0; i < pkl; i++)
			slct.and(this.primaryKeyOrdinals[i], this.getValue(entity, this.primaryKeyOrdinals[i]));

		return slct.exists() ? updateByMembers(entity, this.primaryKeyOrdinals) : insert(entity) ? 1 : 0;

	}

	// Saves an entity based on its identity, unique columns or primary keys
	// Returns the number of entities affected.
	public synchronized final boolean save(Object entity) {

		if (this.identityOrdinal >= 0) {
			Object identityValue = this.columns[identityOrdinal].getValue(entity);
			for (int i = 0; ValueHelper.isNullOrEmpty(identityValue) && i < this.uniqueOrdinals.length; i++) {
				identityValue = select().where(this.uniqueOrdinals[i], getValue(entity, this.uniqueOrdinals[i])).getMemberValue(this.identityOrdinal);
			}
			if (ValueHelper.isNullOrEmpty(identityValue) && this.primaryKeyOrdinals.length > 0) {
				ClassSelect<T> select = select();
				for (int i = 0; i < this.primaryKeyOrdinals.length; i++)
					select.and(this.primaryKeyOrdinals[i], getValue(entity, this.primaryKeyOrdinals[i]));
				identityValue = select.getMemberValue(identityOrdinal);
			}
			if (ValueHelper.isNullOrEmpty(identityValue)) {
				return insert(entity);
			} else {
				throw new RuntimeException("Not implemented");
			}
		}

		for (int i = 0; i < this.uniqueOrdinals.length; i++) {
			if (existsWithMember(entity, this.uniqueOrdinals[i]))
				return updateByMember(entity, uniqueOrdinals[i]) > 0;
		}

		if (this.primaryKeyOrdinals.length > 0 && existsWithMembers(entity, primaryKeyOrdinals))
			return updateByMembers(entity, this.primaryKeyOrdinals) > 0;

		return insert(entity);
	}
}
