package com.eixox.data.entities;

import java.util.LinkedList;
import java.util.List;

import com.eixox.data.Aggregate;
import com.eixox.data.ColumnType;
import com.eixox.data.Delete;
import com.eixox.data.Insert;
import com.eixox.data.Select;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class EntityStorage<T> {

	public final Storage storage;
	public final EntityAspect<T> aspect;

	public EntityStorage(Storage storage, EntityAspect<T> aspect) {
		this.aspect = aspect;
		this.storage = storage;
	}

	public final EntitySelect<T> select() {
		return new EntitySelect<T>(this);
	}

	public final T selectById(Object id) {
		if (this.aspect.identity == null)
			throw new RuntimeException(this.aspect.entityClass + " has no identity set.");
		else if (!aspect.isIdentity(id))
			return null;
		else
			return select().where(aspect.identity, id).first();
	}

	public final T selectByMember(String name, Object value) {
		return select().where(aspect.get(name), value).first();
	}

	public final T selectByCompositeKeys(Object... keys) {
		EntitySelect<T> select = select();
		for (int i = 0; i < aspect.compositeKeys.length; i++)
			select.andWhere(aspect.compositeKeys[i], keys[i]);
		return select.first();
	}

	public final EntityDelete<T> delete() {
		return new EntityDelete<T>(this);
	}

	private final Delete buildDelete(T entity) {
		if (aspect.identity != null) {
			Object id = aspect.identity.getValue(entity);
			if (aspect.isIdentity(id))
				return this.storage.delete().where(aspect.identity.columName, id);
		}
		if (aspect.uniques != null) {
			for (int i = 0; i < aspect.uniques.length; i++) {
				Object uq = aspect.uniques[i].getValue(entity);
				if (uq != null)
					return this.storage.delete().where(aspect.uniques[i].columName, uq);
			}
		}
		if (aspect.compositeKeys != null && aspect.compositeKeys.length > 0) {
			Delete delete = this.storage.delete();
			for (int i = 0; i < aspect.compositeKeys.length; i++)
				delete.andWhere(aspect.compositeKeys[i].columName, aspect.compositeKeys[i].getValue(entity));
		}
		throw new RuntimeException("Can't find ways to delete " + entity);
	}

	public final boolean deleteById(Object id) {
		if (this.aspect.identity == null)
			throw new RuntimeException(this.aspect.entityClass + " has no identity set.");
		else if (!aspect.isIdentity(id))
			return false;
		else
			return storage.delete().where(aspect.identity.columName, id).execute() > 0;
	}

	public final boolean deleteByMember(String name, Object value) {
		return storage.delete().where(aspect.get(name).columName, value).execute() > 0;
	}

	public final long deleteByCompositeKeys(Object... keys) {
		Delete delete = storage.delete();
		for (int i = 0; i < aspect.compositeKeys.length; i++)
			delete.andWhere(aspect.compositeKeys[i].columName, keys[i]);
		return delete.execute();
	}

	public final long delete(T entity) {
		return buildDelete(entity).execute();
	}

	public final long delete(T... entities) {
		List<Delete> deletes = new LinkedList<Delete>();
		for (int i = 0; i < entities.length; i++)
			deletes.add(buildDelete(entities[i]));
		return storage.bulkDelete(deletes);
	}

	public final long delete(Iterable<T> entities) {
		List<Delete> deletes = new LinkedList<Delete>();
		for (T entity : entities)
			deletes.add(buildDelete(entity));
		return storage.bulkDelete(deletes);
	}

	public final EntityUpdate<T> update() {
		return new EntityUpdate<T>(this);
	}

	public final boolean updateByMember(T entity, EntityAspectMember<T> member, Object value) {
		return buildUpdateByMember(entity, member, value).execute() > 0;
	}

	public final boolean updateByMember(T entity, String name, Object value) {
		return buildUpdateByMember(entity, aspect.get(name), value).execute() > 0;
	}

	private final Update buildUpdateByMember(T entity, EntityAspectMember<T> member, Object value) {
		Update update = storage.update();
		for (EntityAspectMember<T> child : aspect)
			if (child != member)
				if (child.columnType != ColumnType.IDENTITY)
					if (child.updatable)
						update.put(child.columName, child.getValue(entity));

		update.where(member.columName, value);
		return update;
	}

	private final Update buildUpdateByCompositeKeys(T entity) {
		Update update = storage.update();
		for (EntityAspectMember<T> child : aspect)
			if (child.columnType != ColumnType.COMPOSITE_KEY)
				if (child.columnType != ColumnType.IDENTITY)
					if (child.updatable)
						update.put(child.columName, child.getValue(entity));

		for (int i = 0; i < aspect.compositeKeys.length; i++)
			update.andWhere(aspect.compositeKeys[i].columName, aspect.compositeKeys[i].getValue(entity));
		return update;
	}

	private final Update buildUpdate(T entity) {
		if (aspect.identity != null) {
			Object id = aspect.identity.getValue(entity);
			if (aspect.isIdentity(id))
				return buildUpdateByMember(entity, aspect.identity, id);
		}
		if (aspect.uniques != null)
			for (int i = 0; i < aspect.uniques.length; i++) {
				Object uq = aspect.uniques[i].getValue(entity);
				if (uq != null)
					return buildUpdateByMember(entity, aspect.uniques[i], uq);
			}
		if (aspect.compositeKeys != null && aspect.compositeKeys.length > 0) {
			return buildUpdateByCompositeKeys(entity);
		}
		throw new RuntimeException("Can't find ways to update " + entity);
	}

	public final long update(T entity) {
		return buildUpdate(entity).execute();
	}

	public final void update(T... entity) {
		for (int i = 0; i < entity.length; i++) {
			Update up = buildUpdate(entity[i]);
			if (up != null)
				up.execute();
		}
	}

	public final synchronized long update(Iterable<T> entities) {
		List<Update> list = new LinkedList<Update>();
		for (T entity : entities) {
			Update up = buildUpdate(entity);
			if (up != null)
				list.add(up);
		}
		return this.storage.bulkUpdate(list);
	}

	public final synchronized void insert(T entity) {
		Insert insert = this.storage.insert();
		for (EntityAspectMember<T> member : this.aspect)
			if (member.columnType != ColumnType.IDENTITY)
				if (member.aggregate == Aggregate.NONE)
					insert.columns.add(member.columName);
		Object[] row = new Object[insert.columns.size()];
		int j = 0;
		for (EntityAspectMember<T> member : this.aspect)
			if (member.columnType != ColumnType.IDENTITY)
				if (member.aggregate == Aggregate.NONE)
					row[j++] = member.getValue(entity);
		insert.add(row);
		if (aspect.identity != null) {
			insert.execute(true);
			aspect.identity.setValue(entity, insert.get(0).generatedKey);
		} else
			insert.execute(false);
	}

	public final synchronized void insert(T... entity) {
		Insert insert = this.storage.insert();
		for (EntityAspectMember<T> member : this.aspect)
			if (member.columnType != ColumnType.IDENTITY)
				if (member.aggregate == Aggregate.NONE)
					insert.columns.add(member.columName);

		for (int i = 0; i < entity.length; i++) {
			Object[] row = new Object[insert.columns.size()];
			int j = 0;
			for (EntityAspectMember<T> member : this.aspect)
				if (member.columnType != ColumnType.IDENTITY)
					if (member.aggregate == Aggregate.NONE)
						row[j++] = member.getValue(entity[i]);
			insert.add(row);
		}

		if (aspect.identity != null) {
			insert.execute(true);
			for (int i = 0; i < entity.length; i++)
				aspect.identity.setValue(entity[i], insert.get(i).generatedKey);
		} else {
			insert.execute(false);
		}
	}

	public final synchronized void insert(List<T> entities) {
		int s = entities.size();
		Insert insert = this.storage.insert();
		for (EntityAspectMember<T> member : this.aspect)
			if (member.columnType != ColumnType.IDENTITY)
				if (member.aggregate == Aggregate.NONE)
					insert.columns.add(member.columName);

		int c = insert.columns.size();
		for (int i = 0; i < s; i++) {
			Object[] row = new Object[c];
			T entity = entities.get(i);
			int j = 0;
			for (EntityAspectMember<T> member : this.aspect)
				if (member.columnType != ColumnType.IDENTITY)
					if (member.aggregate == Aggregate.NONE)
						row[j++] = member.getValue(entity);
			insert.add(row);
		}

		if (aspect.identity != null) {
			insert.execute(true);
			for (int i = 0; i < s; i++)
				aspect.identity.setValue(entities.get(i), insert.get(i).generatedKey);
		} else {
			insert.execute(false);
		}
	}

	public final synchronized Object locateIdentity(T entity) {
		if (aspect.identity != null) {
			Object id = aspect.identity.getValue(entity);
			if (aspect.isIdentity(id))
				return id;

			if (aspect.uniques != null)
				for (int i = 0; i < aspect.uniques.length; i++) {
					id = storage
							.select()
							.addColumn(aspect.identity.columName)
							.where(aspect.uniques[i].columName, aspect.uniques[i].getValue(entity))
							.scalar();
					if (aspect.isIdentity(id))
						return id;
				}

			if (aspect.compositeKeys != null && aspect.compositeKeys.length > 0) {
				Select select = storage.select().addColumn(aspect.identity.columName);
				for (int i = 0; i < aspect.compositeKeys.length; i++)
					select.where(aspect.compositeKeys[i].columName, aspect.compositeKeys[i].getValue(entity));
				return select.scalar();
			} else
				return null;
		} else
			return null;
	}

	public final synchronized boolean save(T entity) {
		Object id = locateIdentity(entity);
		if (id != null)
			return updateByMember(entity, aspect.identity, id);
		else {
			if (aspect.uniques != null) {
				for (int i = 0; i < aspect.uniques.length; i++) {
					Object uq = aspect.uniques[i].getValue(entity);
					if (uq != null)
						if (storage.select().where(aspect.uniques[i].columName, uq).exists())
							return updateByMember(entity, aspect.uniques[i], uq);
				}
			}
			if (aspect.compositeKeys != null && aspect.compositeKeys.length > 0) {
				Select select = storage.select();
				for (int i = 0; i < aspect.compositeKeys.length; i++)
					select.where(aspect.compositeKeys[i].columName, aspect.compositeKeys[i].getValue(entity));
				if (select.exists()) {
					return buildUpdateByCompositeKeys(entity).execute() > 0;
				}
			}
		}
		insert(entity);
		return true;
	}

	public final void save(Iterable<T> entities) {
		for (T entity : entities)
			save(entity);
	}

}
