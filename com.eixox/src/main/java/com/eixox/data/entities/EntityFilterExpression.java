package com.eixox.data.entities;

import com.eixox.data.FilterComparison;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterType;

public final class EntityFilterExpression implements EntityFilter {

	public final EntityFilterNode first;
	private EntityFilterNode last;

	public EntityFilterExpression(EntityFilter filter) {
		this.first = new EntityFilterNode(filter);
		this.last = this.first;
	}

	public EntityFilterExpression(EntityAspect aspect, int ordinal, FilterComparison comparison, Object value) {
		this(new EntityFilterTerm(aspect, ordinal, comparison, value));
	}

	public EntityFilterExpression(EntityAspect aspect, int ordinal, Object value) {
		this(new EntityFilterTerm(aspect, ordinal, value));
	}

	public EntityFilterExpression(EntityAspect aspect, String name, FilterComparison comparison, Object value) {
		this(new EntityFilterTerm(aspect, name, comparison, value));
	}

	public EntityFilterExpression(EntityAspect aspect, String name, Object value) {
		this(new EntityFilterTerm(aspect, name, value));
	}

	public final EntityFilterExpression and(EntityFilter filter) {
		this.last.next = new EntityFilterNode(filter);
		this.last.operation = FilterOperation.AND;
		this.last = this.last.next;
		return this;
	}

	public final EntityFilterExpression and(int ordinal, FilterComparison comparison, Object value) {
		return and(new EntityFilterTerm(this.first.getAspect(), ordinal, comparison, value));
	}

	public final EntityFilterExpression and(int ordinal, Object value) {
		return and(new EntityFilterTerm(this.first.getAspect(), ordinal, value));
	}

	public final EntityFilterExpression and(String name, FilterComparison comparison, Object value) {
		return and(new EntityFilterTerm(this.first.getAspect(), name, comparison, value));
	}

	public final EntityFilterExpression and(String name, Object value) {
		return and(new EntityFilterTerm(this.first.getAspect(), name, value));
	}

	public final EntityFilterExpression or(EntityFilter filter) {
		this.last.next = new EntityFilterNode(filter);
		this.last.operation = FilterOperation.OR;
		this.last = this.last.next;
		return this;
	}

	public final EntityFilterExpression or(int ordinal, FilterComparison comparison, Object value) {
		return or(new EntityFilterTerm(this.first.getAspect(), ordinal, comparison, value));
	}

	public final EntityFilterExpression or(int ordinal, Object value) {
		return or(new EntityFilterTerm(this.first.getAspect(), ordinal, value));
	}

	public final EntityFilterExpression or(String name, FilterComparison comparison, Object value) {
		return or(new EntityFilterTerm(this.first.getAspect(), name, comparison, value));
	}

	public final EntityFilterExpression or(String name, Object value) {
		return or(new EntityFilterTerm(this.first.getAspect(), name, value));
	}

	public final EntityAspect getAspect() {
		return this.first.getAspect();
	}

	public final FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}
}
