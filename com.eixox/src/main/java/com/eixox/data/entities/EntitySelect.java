package com.eixox.data.entities;

import java.util.Iterator;
import java.util.List;

import com.eixox.data.Aggregate;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterTerm;
import com.eixox.data.Filterable;
import com.eixox.data.Pageable;
import com.eixox.data.Select;
import com.eixox.data.SelectAggregate;
import com.eixox.data.SortDirection;
import com.eixox.data.Sortable;

public class EntitySelect<T> implements
		Filterable<EntitySelect<T>>,
		Sortable<EntitySelect<T>>,
		Pageable<EntitySelect<T>>,
		Iterable<T> {

	public final EntityStorage<T> storage;
	private final Select select;

	public EntitySelect(EntityStorage<T> storage) {
		this.storage = storage;
		this.select = this.storage.storage.select();
		for (EntityAspectMember<T> member : storage.aspect) {
			if (member.aggregate != Aggregate.NONE)
				this.select.aggregates.add(new SelectAggregate(member.aggregate, member.columName, member.columName));
			else
				this.select.columns.add(member.columName);
		}
	}

	public final long count() {
		return this.select.count();
	}

	public final boolean exists() {
		return this.select.exists();
	}

	public final T first() {
		return this.select.first(storage.aspect);
	}

	public final List<T> toList() {
		return this.select.transform(storage.aspect);
	}

	public final int toList(List<T> list) {
		return this.select.transform(storage.aspect, list);
	}

	public final Iterator<T> iterator() {
		return toList().iterator();
	}

	public final EntitySelect<T> page(int pageSize, int pageOrdinal) {
		this.select.page(pageSize, pageOrdinal);
		return this;
	}

	public final EntitySelect<T> limit(int pageSize) {
		this.select.limit = pageSize;
		return this;
	}

	public final EntitySelect<T> offset(int offset) {
		this.select.offset = offset;
		return this;
	}

	public final EntitySelect<T> orderBy(String name, SortDirection direction) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.select.sort.orderBy(member.aggregate, member.columName, direction);
		return this;
	}

	public final EntitySelect<T> orderBy(String name) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.select.sort.orderBy(member.aggregate, member.columName, SortDirection.ASC);
		return this;
	}

	public final EntitySelect<T> thenOrderBy(String name, SortDirection direction) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.select.sort.thenOrderBy(member.aggregate, member.columName, direction);
		return this;
	}

	public final EntitySelect<T> thenOrderBy(String name) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		this.select.sort.thenOrderBy(member.aggregate, member.columName, SortDirection.ASC);
		return this;
	}

	public final EntitySelect<T> where(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		if (member.aggregate != Aggregate.NONE)
			select.having.having(member.aggregate, member.columName, term.comparison, term.value);
		else
			select.filter.where(member.columName, term.comparison, term.value);
		return this;
	}

	public final EntitySelect<T> where(FilterExpression expression) {
		storage.aspect.transformFilter(expression, select.filter.clear(), select.having.clear(),
				FilterOperation.AND);
		return this;
	}

	public final EntitySelect<T> where(String name, FilterComparison comparison, Object value) {
		return where(storage.aspect.get(name), comparison, value);
	}

	public final EntitySelect<T> where(String name, Object value) {
		return where(storage.aspect.get(name), value);
	}

	public EntitySelect<T> where(EntityAspectMember<T> member, Object value) {
		if (member.aggregate != Aggregate.NONE)
			select.having.having(member.aggregate, member.columName, value);
		else
			select.filter.where(member.columName, value);
		return this;
	}

	public EntitySelect<T> where(EntityAspectMember<T> member, FilterComparison comparison, Object value) {
		if (member.aggregate != Aggregate.NONE)
			select.having.having(member.aggregate, member.columName, comparison, value);
		else
			select.filter.where(member.columName, comparison, value);
		return this;
	}

	public final EntitySelect<T> andWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		if (member.aggregate != Aggregate.NONE)
			select.having.andHaving(member.aggregate, member.columName, term.comparison, term.value);
		else
			select.filter.andWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public final EntitySelect<T> andWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, select.filter, select.having, FilterOperation.AND);
		return this;
	}

	public final EntitySelect<T> andWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		if (member.aggregate != Aggregate.NONE)
			select.having.andHaving(member.aggregate, member.columName, comparison, value);
		else
			select.filter.andWhere(member.columName, comparison, value);
		return this;
	}

	public final EntitySelect<T> andWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		if (member.aggregate != Aggregate.NONE)
			select.having.andHaving(member.aggregate, member.columName, value);
		else
			select.filter.andWhere(member.columName, value);
		return this;
	}

	public EntitySelect<T> andWhere(EntityAspectMember<T> member, Object value) {
		this.select.andWhere(member.columName, value);
		return this;
	}

	public EntitySelect<T> andWhere(EntityAspectMember<T> member, FilterComparison comparison, Object value) {
		this.select.andWhere(member.columName, comparison, value);
		return this;
	}

	public final EntitySelect<T> orWhere(FilterTerm term) {
		EntityAspectMember<T> member = storage.aspect.get(term.name);
		if (member.aggregate != Aggregate.NONE)
			select.having.orHaving(member.aggregate, member.columName, term.comparison, term.value);
		else
			select.filter.orWhere(member.columName, term.comparison, term.value);
		return this;
	}

	public final EntitySelect<T> orWhere(FilterExpression expression) {
		storage.aspect.transformFilter(expression, select.filter, select.having, FilterOperation.OR);
		return this;
	}

	public final EntitySelect<T> orWhere(String name, FilterComparison comparison, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		if (member.aggregate != Aggregate.NONE)
			select.having.orHaving(member.aggregate, member.columName, comparison, value);
		else
			select.filter.orWhere(member.columName, comparison, value);
		return this;
	}

	public final EntitySelect<T> orWhere(String name, Object value) {
		EntityAspectMember<T> member = storage.aspect.get(name);
		if (member.aggregate != Aggregate.NONE)
			select.having.orHaving(member.aggregate, member.columName, value);
		else
			select.filter.orWhere(member.columName, value);
		return this;
	}

	public EntitySelect<T> orWhere(EntityAspectMember<T> member, Object value) {
		this.select.orWhere(member.columName, value);
		return this;
	}

	public EntitySelect<T> orWhere(EntityAspectMember<T> member, FilterComparison comparison, Object value) {
		this.select.orWhere(member.columName, comparison, value);
		return this;
	}

}
