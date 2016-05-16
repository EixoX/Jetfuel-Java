package com.eixox.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.eixox.data.entities.EntityAspect;

public abstract class Select implements
		Filterable<Select>,
		FilterableAggregate<Select>,
		Sortable<Select>,
		SortableAggregate<Select>,
		Pageable<Select>,
		Iterable<Object[]> {

	public final ArrayList<String> columns = new ArrayList<String>();
	public final ArrayList<SelectAggregate> aggregates = new ArrayList<SelectAggregate>();
	public final SortExpression sort = new SortExpression();
	public final FilterExpression filter = new FilterExpression();
	public final AggregateFilterExpression having = new AggregateFilterExpression();
	public int offset;
	public int limit;

	public abstract SelectResult toResult();

	public abstract long count();

	public abstract boolean exists();

	public abstract Object scalar();

	public abstract Object[] first();

	public abstract <T> int transform(EntityAspect<T> aspect, List<T> target);

	public final <T> List<T> transform(EntityAspect<T> aspect) {
		ArrayList<T> list = new ArrayList<T>(this.limit > 0 ? this.limit : 10);
		transform(aspect, list);
		return list;
	}

	public abstract <T> T first(EntityAspect<T> aspect);

	public final Iterator<Object[]> iterator() {
		return toResult().rows.iterator();
	}

	public final Select page(int pageSize, int pageOrdinal) {
		this.offset = pageSize * pageOrdinal;
		this.limit = pageSize;
		return this;
	}

	public final Select limit(int pageSize) {
		this.limit = pageSize;
		return this;
	}

	public final Select offset(int offset) {
		this.offset = offset;
		return this;
	}

	public final Select orderBy(Aggregate aggregate, String name, SortDirection direction) {
		this.sort.orderBy(aggregate, name, direction);
		return this;
	}

	public final Select orderBy(String name, SortDirection direction) {
		this.sort.orderBy(name, direction);
		return this;
	}

	public final Select orderBy(String name) {
		this.sort.orderBy(name);
		return this;
	}

	public final Select thenOrderBy(Aggregate aggregate, String name, SortDirection direction) {
		this.sort.thenOrderBy(aggregate, name, direction);
		return this;
	}

	public final Select thenOrderBy(String name, SortDirection direction) {
		this.sort.thenOrderBy(name, direction);
		return this;
	}

	public final Select thenOrderBy(String name) {
		this.sort.thenOrderBy(name);
		return this;
	}

	public final Select having(AggregateFilterExpression expression) {
		this.having.having(expression);
		return this;
	}

	public final Select having(AggregateFilterTerm term) {
		this.having.having(term);
		return this;
	}

	public final Select having(Aggregate aggregate, String name, FilterComparison comparison, Object value) {
		this.having.having(aggregate, name, comparison, value);
		return this;
	}

	public final Select having(Aggregate aggregate, String name, Object value) {
		this.having.having(aggregate, name, value);
		return this;
	}

	public final Select andHaving(AggregateFilterExpression expression) {
		this.having.andHaving(expression);
		return this;
	}

	public final Select andHaving(AggregateFilterTerm term) {
		this.having.andHaving(term);
		return this;
	}

	public final Select andHaving(Aggregate aggregate, String name, FilterComparison comparison, Object value) {
		this.having.andHaving(aggregate, name, comparison, value);
		return this;
	}

	public final Select andHaving(Aggregate aggregate, String name, Object value) {
		this.having.andHaving(aggregate, name, value);
		return this;
	}

	public final Select orHaving(AggregateFilterExpression expression) {
		this.having.orHaving(expression);
		return this;
	}

	public final Select orHaving(AggregateFilterTerm term) {
		this.having.orHaving(term);
		return this;
	}

	public final Select orHaving(Aggregate aggregate, String name, FilterComparison comparison, Object value) {
		this.having.orHaving(aggregate, name, comparison, value);
		return this;
	}

	public final Select orHaving(Aggregate aggregate, String name, Object value) {
		this.having.orHaving(aggregate, name, value);
		return this;
	}

	public final Select where(FilterTerm term) {
		this.filter.where(term);
		return this;
	}

	public final Select where(FilterExpression expression) {
		this.filter.where(expression);
		return this;
	}

	public final Select where(String name, FilterComparison comparison, Object value) {
		this.filter.where(name, comparison, value);
		return this;
	}

	public final Select where(String name, Object value) {
		this.filter.where(name, value);
		return this;
	}

	public final Select andWhere(FilterTerm term) {
		this.filter.andWhere(term);
		return this;
	}

	public final Select andWhere(FilterExpression expression) {
		this.filter.andWhere(expression);
		return this;
	}

	public final Select andWhere(String name, FilterComparison comparison, Object value) {
		this.filter.andWhere(name, comparison, value);
		return this;
	}

	public final Select andWhere(String name, Object value) {
		this.filter.andWhere(name, value);
		return this;
	}

	public final Select orWhere(FilterTerm term) {
		this.filter.orWhere(term);
		return this;
	}

	public final Select orWhere(FilterExpression expression) {
		this.filter.orWhere(expression);
		return this;
	}

	public final Select orWhere(String name, FilterComparison comparison, Object value) {
		this.filter.orWhere(name, comparison, value);
		return this;
	}

	public final Select orWhere(String name, Object value) {
		this.filter.orWhere(name, value);
		return this;
	}

	public final Select addColumn(String column) {
		this.columns.add(column);
		return this;
	}

	public final Select addAggregate(Aggregate aggregate, String column) {
		return addAggregate(aggregate, column, column);
	}

	public final Select addAggregate(Aggregate aggregate, String column, String alias) {
		SelectAggregate aggr = new SelectAggregate(aggregate, column, alias);
		this.aggregates.add(aggr);
		return this;
	}

}