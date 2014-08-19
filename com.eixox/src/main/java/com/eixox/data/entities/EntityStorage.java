package com.eixox.data.entities;

import com.eixox.Convert;

public abstract class EntityStorage<T> {

	public final EntityAspect aspect;

	public EntityStorage(EntityAspect aspect) {
		this.aspect = aspect;
	}

	public abstract EntitySelect<T> select();

	public abstract EntityDelete delete();

	public abstract EntityInsert insert();

	public abstract EntityInsertBulk<T> insertBulk();

	public abstract EntityUpdate update();

	public final long delete(T entity) {

		// delete by identity
		if (this.aspect.identityOrdinal >= 0) {
			return delete().where(aspect.identityOrdinal, aspect.getValue(entity, aspect.identityOrdinal)).execute();
		}
		// update by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null)
				{
					return delete().where(uniqueOrdinal, uniqueValue).execute();
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
		{
			return delete().where(aspect.buildCompositeKeyFilter(entity)).execute();
		}
		return -1;

	}

	public final long deleteByIdentity(Object id) {
		if (aspect.identityOrdinal < 0)
			throw new RuntimeException(aspect.getDataType() + " has no identity set");
		else
			return delete().where(aspect.identityOrdinal, id).execute();
	}

	public final long deleteByMember(String memberName, Object value) {
		return delete().where(memberName, value).execute();
	}

	public final long deleteWhere(EntityFilter filter) {
		return delete().where(filter).execute();
	}

	public final T readByIdentity(Object id) {
		if (aspect.identityOrdinal < 0)
			throw new RuntimeException(aspect.getDataType() + " has no identity set");
		else
			return select().where(aspect.identityOrdinal, id).singleResult();
	}

	public final T readByMember(String memberName, Object value) {
		return select().where(memberName, value).singleResult();
	}

	public final long count() {
		return select().count();
	}

	public final boolean exists() {
		return select().exists();
	}

	public final long countWithMember(String memberName, Object value) {
		return select().where(memberName, value).count();
	}

	public final boolean existsWithMember(String memberName, Object value) {
		return select().where(memberName, value).exists();
	}

	public final boolean insert(T entity) {

		final EntityInsert insert = insert();
		insert.set(entity);

		if (aspect.identityOrdinal > 0) {
			return insert.execute();
		}
		else {
			Object identityValue = insert.executeAndScopeIdentity();
			if (identityValue == null)
				return false;
			else {
				aspect.setValue(entity, aspect.identityOrdinal, identityValue);
				return true;
			}
		}
	}

	public final int insert(Iterable<T> iterable) {
		final EntityInsertBulk<T> insertBulk = insertBulk();
		for (T item : iterable)
			insertBulk.add(item);
		return insertBulk.execute();
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
				if (uniqueValue != null)
				{
					identityValue = select().where(uniqueOrdinal, uniqueValue).readMember(aspect.identityOrdinal);
					if (identityValue != null) {
						aspect.setValue(entity, aspect.identityOrdinal, identityValue);
						return identityValue;
					}
				}
			}

		// Check for composite keys that can be used to recover the id
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
		{
			identityValue = select().where(aspect.buildCompositeKeyFilter(entity)).readMember(aspect.identityOrdinal);
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
			return select().where(aspect.identityOrdinal, aspect.getValue(entity, aspect.identityOrdinal)).exists();
		}

		// update by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null)
				{
					return select().where(uniqueOrdinal, uniqueValue).exists();
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
		{
			return select().where(aspect.buildCompositeKeyFilter(entity)).exists();
		}
		return false;
	}

	public final long update(T entity) {

		final EntityUpdate update = update();
		update.set(entity);

		// update by identity
		if (aspect.identityOrdinal >= 0) {
			update.values[aspect.identityOrdinal] = Void.class;
			Object identityValue = aspect.getValue(entity, aspect.identityOrdinal);
			return update.where(aspect.identityOrdinal, identityValue).execute();
		}

		// update by unique members
		if (aspect.uniqueOrdinals != null && aspect.uniqueOrdinals.length > 0)
			for (int i = 0; i < aspect.uniqueOrdinals.length; i++) {
				int uniqueOrdinal = aspect.uniqueOrdinals[i];
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinal);
				if (uniqueValue != null)
				{
					update.values[uniqueOrdinal] = Void.class;
					return update.where(uniqueOrdinal, uniqueValue).execute();
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
		{
			for (int i = 0; i < aspect.compositeKeyOrdinals.length; i++) {
				int compositeOrdinal = aspect.compositeKeyOrdinals[i];
				Object compositeValue = aspect.getValue(entity, compositeOrdinal);
				update.values[compositeOrdinal] = Void.class;
				update.and(compositeOrdinal, compositeValue);
			}
			return update.execute();
		}

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
				if (uniqueValue != null)
				{
					if (select().where(uniqueOrdinal, uniqueValue).exists())
						return update(entity) > 0;
				}
			}

		// update by composite keys
		if (aspect.compositeKeyOrdinals != null && aspect.compositeKeyOrdinals.length > 0)
		{
			if (select().where(aspect.buildCompositeKeyFilter(entity)).exists())
				return update(entity) > 0;
		}

		return insert(entity);
	}
}
