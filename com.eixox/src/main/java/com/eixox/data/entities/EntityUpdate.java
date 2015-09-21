package com.eixox.data.entities;

import com.eixox.data.ColumnType;
import com.eixox.data.DataUpdate;
import com.eixox.data.Storage;

public class EntityUpdate extends EntityFilterBase<EntityUpdate> {

	private final DataUpdate update;
	public final Storage storage;

	public EntityUpdate(EntityAspect aspect, Storage storage) {
		super(aspect);
		this.storage = storage;
		this.update = storage.update(aspect.tableName);
	}

	public final EntityUpdate reset() {
		this.update.reset();
		return this;
	}

	public final EntityUpdate set(int ordinal, Object value) {
		this.update.set(aspect.getColumnName(ordinal), value);
		return this;
	}

	public final EntityUpdate set(String name, Object value) {
		return set(aspect.getOrdinalOrException(name), value);
	}

	public final void set(Object entity) {
		for (EntityAspectMember member : aspect) {
			if (member.columntType != ColumnType.IDENTITY)
				if (!member.readOnly) {
					this.update.set(member.columnName, member.getValue(entity));
				}
		}
	}

	public final Object get(int ordinal) {
		return this.update.get(aspect.getColumnName(ordinal));
	}

	public final Object get(String name) {
		return get(aspect.getOrdinalOrException(name));
	}

	public final long execute() {
		return this.update.execute();
	}

	@Override
	protected final EntityUpdate getThis() {
		return this;
	}
}
