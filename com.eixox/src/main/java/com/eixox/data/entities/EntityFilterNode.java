package com.eixox.data.entities;

import com.eixox.data.FilterComparison;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterType;

public final class EntityFilterNode implements EntityFilter {

	public final EntityFilter filter;
	public FilterOperation operation;
	public EntityFilterNode next;

	public EntityFilterNode(EntityFilter filter) {
		this.filter = filter;
	}

	public EntityFilterNode(EntityAspect aspect, int ordinal, FilterComparison comparison, Object value) {
		this(new EntityFilterTerm(aspect, ordinal, comparison, value));
	}

	public EntityFilterNode(EntityAspect aspect, int ordinal, Object value) {
		this(new EntityFilterTerm(aspect, ordinal, value));
	}

	public EntityFilterNode(EntityAspect aspect, String name, FilterComparison comparison, Object value) {
		this(new EntityFilterTerm(aspect, name, comparison, value));
	}

	public EntityFilterNode(EntityAspect aspect, String name, Object value) {
		this(new EntityFilterTerm(aspect, name, value));
	}

	public final EntityAspect getAspect() {
		return this.filter.getAspect();
	}

	public final FilterType getFilterType() {
		return FilterType.NODE;
	}

}
