package com.eixox.data.entities;

import com.eixox.data.FilterComparison;

public abstract class EntityFilterBase<T> {

	public final EntityAspect aspect;
	public EntityFilterExpression filter;

	protected EntityFilterBase(EntityAspect aspect) {
		this.aspect = aspect;
	}

	protected abstract T getThis();

	public final T where(EntityFilter filter) {
		this.filter = new EntityFilterExpression(filter);
		return getThis();
	}

	public final T where(int ordinal, FilterComparison comparison, Object value) {
		return where(new EntityFilterTerm(aspect, ordinal, comparison, value));
	}

	public final T where(int ordinal, Object value) {
		return where(new EntityFilterTerm(aspect, ordinal, value));
	}

	public final T where(String name, FilterComparison comparison, Object value) {
		return where(new EntityFilterTerm(aspect, name, comparison, value));
	}

	public final T where(String name, Object value) {
		return where(new EntityFilterTerm(aspect, name, value));
	}

	public final T and(EntityFilter filter) {
		this.filter = this.filter == null ? new EntityFilterExpression(filter) : this.filter.and(filter);
		return getThis();
	}

	public final T and(int ordinal, FilterComparison comparison, Object value) {
		return and(new EntityFilterTerm(aspect, ordinal, comparison, value));
	}

	public final T and(int ordinal, Object value) {
		return and(new EntityFilterTerm(aspect, ordinal, value));
	}

	public final T and(String name, FilterComparison comparison, Object value) {
		return and(new EntityFilterTerm(aspect, name, comparison, value));
	}

	public final T and(String name, Object value) {
		return and(new EntityFilterTerm(aspect, name, value));
	}

	public final T or(EntityFilter filter) {
		this.filter = this.filter == null ? new EntityFilterExpression(filter) : this.filter.or(filter);
		return getThis();
	}

	public final T or(int ordinal, FilterComparison comparison, Object value) {
		return or(new EntityFilterTerm(aspect, ordinal, comparison, value));
	}

	public final T or(int ordinal, Object value) {
		return or(new EntityFilterTerm(aspect, ordinal, value));
	}

	public final T or(String name, FilterComparison comparison, Object value) {
		return or(new EntityFilterTerm(aspect, name, comparison, value));
	}

	public final T or(String name, Object value) {
		return or(new EntityFilterTerm(aspect, name, value));
	}
}
