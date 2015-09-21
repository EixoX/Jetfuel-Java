package com.eixox.data.entities;

import com.eixox.data.DataDelete;
import com.eixox.data.Storage;

public class EntityDelete extends EntityFilterBase<EntityDelete> {

	public final Storage storage;

	public EntityDelete(EntityAspect aspect, Storage storage) {
		super(aspect);
		this.storage = storage;
	}

	@Override
	protected final EntityDelete getThis() {
		return this;
	}

	public final long execute() {
		DataDelete delete = storage.delete(this.aspect.tableName);
		delete.filter = this.filter;
		return delete.execute();
	}
}
