package com.eixox.data.entities;

import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterTerm;
import com.eixox.data.Filterable;
import com.eixox.data.Update;

public class EntityUpdate<T> implements Filterable<EntityUpdate<T>> {

	private final Update update;
	public final EntityStorage<T> storage;

	public EntityUpdate(EntityStorage<T> storage) {
		this.update = storage.storage.update();
		this.storage = storage;
	}

	public final EntityUpdate<T> set(String name, Object value) {
		EntityAspectMember<T> member = this.storage.aspect.get(name);
		this.update.put(member.columName, value);
		return this;
	}

	public EntityUpdate<T> where(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.update.where.where(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityUpdate<T> where(FilterExpression expression) {
		storage.aspect.transformFilter(expression, update.where, null, FilterOperation.AND);
		return this;
	}

	public EntityUpdate<T> where(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.where(member.columName, comparison, value);
		return this;
	}

	public EntityUpdate<T> where(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.where(member.columName, value);
		return this;
	}

	public EntityUpdate<T> andWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.update.where.andWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityUpdate<T> andWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, update.where, null, FilterOperation.AND);
		return this;
	}

	public EntityUpdate<T> andWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.andWhere(member.columName, comparison, value);
		return this;
	}

	public EntityUpdate<T> andWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.andWhere(member.columName, value);
		return this;
	}

	public EntityUpdate<T> orWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		this.update.where.orWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public EntityUpdate<T> orWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, update.where, null, FilterOperation.OR);
		return this;
	}

	public EntityUpdate<T> orWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.orWhere(member.columName, comparison, value);
		return this;
	}

	public EntityUpdate<T> orWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.update.where.orWhere(member.columName, value);
		return this;
	}

	public long execute() {
		return this.update.execute();
	}

}
