package com.eixox.data.entities;

import com.eixox.data.SortDirection;

public class EntitySortNode {

	public final EntityAspect aspect;
	public final int ordinal;
	public final SortDirection direction;
	public EntitySortNode next;

	public EntitySortNode(EntityAspect aspect, int ordinal, SortDirection direction) {
		this.aspect = aspect;
		this.ordinal = ordinal;
		this.direction = direction;
	}

	public EntitySortNode(EntityAspect aspect, int ordinal) {
		this(aspect, ordinal, SortDirection.ASCENDING);
	}

	public EntitySortNode(EntityAspect aspect, String name, SortDirection direction) {
		this(aspect, aspect.getOrdinalOrException(name), direction);
	}

	public EntitySortNode(EntityAspect aspect, String name) {
		this(aspect, aspect.getOrdinalOrException(name));
	}

}
