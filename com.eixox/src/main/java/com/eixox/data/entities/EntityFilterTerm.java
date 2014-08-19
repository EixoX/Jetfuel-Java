package com.eixox.data.entities;

import com.eixox.data.FilterComparison;
import com.eixox.data.FilterType;

public final class EntityFilterTerm implements EntityFilter {

	public final EntityAspect aspect;
	public final int ordinal;
	public final FilterComparison comparison;
	public final Object value;

	public EntityFilterTerm(EntityAspect aspect, int ordinal, FilterComparison comparison, Object value) {
		this.aspect = aspect;
		this.ordinal = ordinal;
		this.comparison = comparison;
		this.value = value;
	}

	public EntityFilterTerm(EntityAspect aspect, int ordinal, Object value) {
		this(aspect, ordinal, FilterComparison.EQUAL_TO, value);
	}

	public EntityFilterTerm(EntityAspect aspect, String name, FilterComparison comparison, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), comparison, value);
	}

	public EntityFilterTerm(EntityAspect aspect, String name, Object value) {
		this(aspect, aspect.getOrdinalOrException(name), FilterComparison.EQUAL_TO, value);
	}

	public final EntityAspect getAspect() {
		return this.aspect;
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}
}
