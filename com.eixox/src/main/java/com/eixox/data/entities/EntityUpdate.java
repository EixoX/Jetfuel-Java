package com.eixox.data.entities;

public abstract class EntityUpdate extends EntityFilterBase<EntityUpdate> {

	public final Object[] values;

	public EntityUpdate(EntityAspect aspect) {
		super(aspect);
		this.values = new Object[aspect.getCount()];
	}

	public final EntityUpdate reset() {
		for (int i = 0; i < values.length; i++)
			values[i] = Void.class;
		return this;
	}

	public final EntityUpdate set(int ordinal, Object value) {
		this.values[ordinal] = value;
		return this;
	}

	public final EntityUpdate set(String name, Object value) {
		this.values[aspect.getOrdinalOrException(name)] = value;
		return this;
	}

	public final void set(Object entity) {
		for (int i = 0; i < values.length; i++) {
			EntityAspectMember member = aspect.get(i);
			this.values[i] = member.readOnly ? Void.class : member.getValue(entity);
		}
	}

	public final Object get(int ordinal) {
		return this.values[ordinal];
	}

	public final Object get(String name) {
		return this.values[aspect.getOrdinalOrException(name)];
	}

	public abstract long execute();
	
	@Override
	protected final EntityUpdate getThis() {
		return this;
	}
}
