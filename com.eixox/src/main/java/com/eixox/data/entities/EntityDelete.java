package com.eixox.data.entities;

public abstract class EntityDelete extends EntityFilterBase<EntityDelete> {

	public EntityDelete(EntityAspect aspect) {
		super(aspect);
	}

	@Override
	protected final EntityDelete getThis() {
		return this;
	}
	
	
	public abstract long execute();
}
