package com.eixox.data.entities;

import com.eixox.data.Filter;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterTerm;

public abstract class EntityFilterBase<T> {

	public final EntityAspect aspect;
	public FilterExpression filter;

	protected EntityFilterBase(EntityAspect aspect) {
		this.aspect = aspect;
	}

	protected abstract T getThis();

	public final T where(Filter filter) {
		this.filter = new FilterExpression(filter);
		return getThis();
	}

	public final T where(int ordinal, FilterComparison comparison, Object value) {
		return where(new FilterTerm(aspect.getColumnName(ordinal), comparison, value));
	}

	public final T where(int ordinal, Object value) {
		return where(new FilterTerm(aspect.getColumnName(ordinal), value));
	}

	public final T where(String name, FilterComparison comparison, Object value) {
		return where(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), comparison, value));
	}

	public final T where(String name, Object value) {
		return where(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), value));
	}

	public final T and(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.and(filter);
		return getThis();
	}

	public final T and(int ordinal, FilterComparison comparison, Object value) {
		return and(new FilterTerm(aspect.getColumnName(ordinal), comparison, value));
	}

	public final T and(int ordinal, Object value) {
		return and(new FilterTerm(aspect.getColumnName(ordinal), value));
	}

	public final T and(String name, FilterComparison comparison, Object value) {
		return and(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), comparison, value));
	}

	public final T and(String name, Object value) {
		return and(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), value));
	}

	public final T or(Filter filter) {
		this.filter = this.filter == null ? new FilterExpression(filter) : this.filter.or(filter);
		return getThis();
	}

	public final T or(int ordinal, FilterComparison comparison, Object value) {
		return or(new FilterTerm(aspect.getColumnName(ordinal), comparison, value));
	}

	public final T or(int ordinal, Object value) {
		return or(new FilterTerm(aspect.getColumnName(ordinal), value));
	}

	public final T or(String name, FilterComparison comparison, Object value) {
		return or(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), comparison, value));
	}

	public final T or(String name, Object value) {
		return or(new FilterTerm(aspect.getColumnName(aspect.getOrdinalOrException(name)), value));
	}
}
