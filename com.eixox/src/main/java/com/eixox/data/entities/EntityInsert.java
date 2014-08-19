package com.eixox.data.entities;

public abstract class EntityInsert {

	public final EntityAspect aspect;
	public final Object[] values;

	public EntityInsert(EntityAspect aspect) {
		this.aspect = aspect;
		this.values = new Object[aspect.getCount()];
	}

	public final void reset() {
		for (int i = 0; i < values.length; i++)
			values[i] = null;
	}

	public final void set(int ordinal, Object value) {
		this.values[ordinal] = value;
	}

	public final void set(String name, Object value) {
		this.values[aspect.getOrdinalOrException(name)] = value;
	}

	public final void set(Object entity) {
		for (int i = 0; i < values.length; i++)
		{
			EntityAspectMember member = aspect.get(i);
			this.values[i] = member.readOnly ? Void.class : member.getValue(entity);
		}
	}

	public abstract boolean execute();

	public abstract Object executeAndScopeIdentity();
}
