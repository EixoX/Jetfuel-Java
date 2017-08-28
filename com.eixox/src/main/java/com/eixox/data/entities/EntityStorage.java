package com.eixox.data.entities;

import com.eixox.Convert;
import com.eixox.data.DataUpdate;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.Storage;
import com.eixox.reflection.AspectMember;

public class EntityStorage<T> {

	public final EntityAspect aspect;
	public final Storage storage;

	public EntityStorage(EntityAspect aspect, Storage storage) {
		this.aspect = aspect;
		this.storage = storage;
	}

	public EntityStorage(Storage dataStorage, Class<T> claz) {
		this.aspect = EntityAspect.getDefaultInstance(claz);
		this.storage = dataStorage;
	}

	public final EntitySelect<T> select() {
		return new EntitySelect<T>(this.aspect, this.storage);
	}

	public final EntityDelete delete() {
		return new EntityDelete(this.aspect, this.storage);
	}

	public final EntityInsert insert() {
		return new EntityInsert(this.aspect, this.storage);
	}

	public final EntityUpdate update() {
		return new EntityUpdate(this.aspect, this.storage);
	}
	
	public final EntitySelect<T> search(String filter) {
		if(filter == null | filter.isEmpty()){
			return null;
		} else if(filter.length() < 3){
			filter = filter + "%";
		} else {
			filter = "%" + filter.replace(' ', '%') + "%";
		}
		
		FilterExpression expression = null;
		int count = this.aspect.getCount();
		
		for(int i = 0; i < count; i++){
			expression = 
					expression == null ? 
							new FilterExpression(aspect.getName(i), FilterComparison.LIKE, filter) :
							expression.or(aspect.getName(i), FilterComparison.LIKE, filter);
		}		
		
		return new EntitySelect<T>(this.aspect, this.storage).where(expression);
	}

	public final long delete(T entity) {

		// delete by identity
		if (this.aspect.identityOrdinal >= 0) {
			EntityAspectMember identity = this.aspect.get(this.aspect.identityOrdinal);
			return this.storage.delete(this.aspect.tableName).where(identity.columnName, identity.getValue(entity))
					.execute();
		}
		// delete by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				EntityAspectMember uniqueMember = aspect.get(aspect.uniqueOrdinals[i]);
				Object uniqueValue = uniqueMember.getValue(entity);
				if (uniqueValue != null) {
					return this.storage.delete(this.aspect.tableName).where(uniqueMember.columnName, uniqueValue)
							.execute();
				}
			}
		// delete by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0) {
			return this.storage.delete(this.aspect.tableName).where(aspect.buildCompositeKeyFilter(entity)).execute();
		}

		return -1;

	}

	public final long deleteByIdentity(Object id) {
		EntityAspectMember identity = aspect.getIdentity();
		if (identity == null)
			throw new RuntimeException(aspect.getDataType() + " has no identity set");
		else
			return this.storage.delete(this.aspect.tableName).where(identity.columnName, id).execute();
	}

	public final long deleteByMember(String memberName, Object value) {
		EntityAspectMember member = aspect.get(memberName);
		return this.storage.delete(this.aspect.tableName).where(member.columnName, value).execute();
	}

	public final T readByIdentity(Object id) {
		EntityAspectMember identity = aspect.getIdentity();
		if (identity == null)
			throw new RuntimeException(aspect.getDataType() + " has no identity set");
		else
			return this.storage.select(this.aspect.tableName).where(identity.columnName, id).getEntity(this.aspect);
	}

	public final T readByMember(String memberName, Object value) {
		return this.storage.select(this.aspect.tableName)
				.where(this.aspect.getColumnName(aspect.getOrdinalOrException(memberName)), value)
				.getEntity(this.aspect);
	}

	public final long count() {
		return this.storage.select(this.aspect.tableName).count();
	}

	public final boolean exists() {
		return this.storage.select(this.aspect.tableName).exists();
	}

	public final long countWithMember(String memberName, Object value) {
		return this.storage.select(this.aspect.tableName)
				.where(this.aspect.getColumnName(aspect.getOrdinalOrException(memberName)), value).count();
	}

	public final boolean existsWithMember(String memberName, Object value) {
		return this.storage.select(this.aspect.tableName)
				.where(this.aspect.getColumnName(aspect.getOrdinalOrException(memberName)), value).exists();
	}

	public final boolean insert(T entity) {

		final EntityInsert insert = insert();
		insert.add(entity);

		if (aspect.identityOrdinal < 0) {
			return insert.execute() > 0;
		} else {
			Object identityValue = insert.executeAndScopeIdentity();
			if (identityValue == null)
				return false;
			else {
				AspectMember identity = aspect.getIdentity();
				identityValue = Convert.changeType(identityValue, identity.getDataType());
				identity.setValue(entity, identityValue);
				return true;
			}
		}
	}

	public final long insert(Iterable<T> iterable) {
		EntityInsert insert = insert();
		for (T item : iterable)
			insert.add(item);
		return insert.execute();
	}

	public final Object ensureIdentity(T entity) {

		// It needs an identity ordinal.
		if (aspect.identityOrdinal < 0)
			return null;

		// Check an already set identity
		Object identityValue = aspect.getValue(entity, aspect.identityOrdinal);
		if (!Convert.isNullOrEmpty(identityValue))
			return identityValue;

		// Check for unique items that can be used to recover the identity
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null) {
					identityValue = this.storage.select(aspect.tableName)
							.where(aspect.getColumnName(uniqueOrdinal), uniqueValue)
							.getFirstMember(aspect.getColumnName(aspect.identityOrdinal));
					if (identityValue != null) {
						aspect.setValue(entity, aspect.identityOrdinal, identityValue);
						return identityValue;
					}
				}
			}

		// Check for composite keys that can be used to recover the id
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0) {
			identityValue = this.storage.select(aspect.tableName).where(aspect.buildCompositeKeyFilter(entity))
					.getFirstMember(aspect.getColumnName(aspect.identityOrdinal));

			if (identityValue != null) {
				aspect.setValue(entity, aspect.identityOrdinal, identityValue);
				return identityValue;
			}
		}

		return null;

	}

	public final boolean exists(T entity) {
		// update by identity
		if (aspect.identityOrdinal >= 0) {
			return storage.select(aspect.tableName).where(aspect.getColumnName(aspect.identityOrdinal),
					aspect.getValue(entity, aspect.identityOrdinal)).exists();
		}

		// update by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null) {
					return select().where(uniqueOrdinal, uniqueValue).exists();
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0) {
			return select().where(aspect.buildCompositeKeyFilter(entity)).exists();
		}
		return false;
	}

	private final long updateByIdentity(T entity) {
		DataUpdate update = this.storage.update(aspect.tableName).where(aspect.getColumnName(aspect.identityOrdinal),
				aspect.getValue(entity, aspect.identityOrdinal));

		int memberCount = this.aspect.getCount();
		for (int i = 0; i < memberCount; i++)
			if (i != aspect.identityOrdinal) {
				EntityAspectMember member = aspect.get(i);
				if (!member.readOnly)
					update.set(member.columnName, member.getValue(entity));
			}

		return update.execute();
	}

	private final long updateByUniqueMember(T entity, int uniqueOrdinal, Object uniqueValue) {

		DataUpdate update = this.storage.update(aspect.tableName).where(aspect.getColumnName(uniqueOrdinal),
				uniqueValue);

		int memberCount = this.aspect.getCount();
		for (int i = 0; i < memberCount; i++) {
			if (i != aspect.identityOrdinal && i != uniqueOrdinal) {
				EntityAspectMember member = aspect.get(i);
				if (!member.readOnly)
					update.set(member.columnName, member.getValue(entity));
			}
		}

		return update.execute();
	}

	private final long updateByCompositeKey(T entity, FilterExpression compositeKeyFilter) {

		DataUpdate update = this.storage.update(aspect.tableName).where(compositeKeyFilter);

		int memberCount = this.aspect.getCount();
		for (int i = 0; i < memberCount; i++)
			if (i != aspect.identityOrdinal && !aspect.isCompositeKeyMember(i)) {
				EntityAspectMember member = aspect.get(i);
				if (!member.readOnly)
					update.set(member.columnName, member.getValue(entity));
			}
		return update.execute();
	}

	public final long update(T entity) {
		// update by identity
		if (aspect.identityOrdinal >= 0)
			return updateByIdentity(entity);

		// update by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null)
					return updateByUniqueMember(entity, uniqueOrdinal, uniqueValue);
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
			return updateByCompositeKey(entity, aspect.buildCompositeKeyFilter(entity));

		return -1L;

	}

	public final boolean save(T entity) {
		if (aspect.identityOrdinal >= 0) {
			return ensureIdentity(entity) != null ? update(entity) > 0 : insert(entity);
		}

		// save by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null) {
					if (select().where(uniqueOrdinal, uniqueValue).exists())
						return update(entity) > 0;
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0) {
			if (select().where(aspect.buildCompositeKeyFilter(entity)).exists())
				return update(entity) > 0;
		}

		return insert(entity);
	}
}
