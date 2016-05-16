package com.eixox.data.entities;

import com.eixox.data.Delete;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterTerm;
import com.eixox.data.Filterable;

public class EntityDelete<T> implements Filterable<EntityDelete<T>> {

	private final Delete delete;
	public final EntityStorage<T> storage;

	public EntityDelete(EntityStorage<T> storage) {
		this.delete = storage.storage.delete();
		this.storage = storage;
	}

	public EntityDelete<T> where(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.delete.where.where(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityDelete<T> where(FilterExpression expression) {
		storage.aspect.transformFilter(expression, delete.where, null, FilterOperation.AND);
		return this;
	}

	public EntityDelete<T> where(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.where(member.columName, comparison, value);
		return this;
	}

	public EntityDelete<T> where(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.where(member.columName, value);
		return this;
	}

	public EntityDelete<T> andWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.delete.where.andWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityDelete<T> andWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, delete.where, null, FilterOperation.AND);
		return this;
	}

	public EntityDelete<T> andWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.andWhere(member.columName, comparison, value);
		return this;
	}

	public EntityDelete<T> andWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.andWhere(member.columName, value);
		return this;
	}

	public EntityDelete<T> orWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.delete.where.orWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityDelete<T> orWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, delete.where, null, FilterOperation.OR);
		return this;
	}

	public EntityDelete<T> orWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.orWhere(member.columName, comparison, value);
		return this;
	}

	public EntityDelete<T> orWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.delete.where.orWhere(member.columName, value);
		return this;
	}

	public long execute() {
		return this.delete.execute();
	}

}
