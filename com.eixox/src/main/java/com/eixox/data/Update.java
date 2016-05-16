package com.eixox.data;

import java.util.HashMap;

public abstract class Update extends HashMap<String, Object> implements Filterable<Update> {

	private static final long serialVersionUID = 4946226871353014207L;
	public final FilterExpression where = new FilterExpression();

	public final Update set(String name, Object value) {
		super.put(name, value);
		return this;
	}

	public final Update where(FilterTerm term) {
		this.where.where(term);
		return this;
	}

	public final Update where(FilterExpression expression) {
		this.where.where(expression);
		return this;
	}

	public final Update where(String name, FilterComparison comparison, Object value) {
		this.where.where(name, comparison, value);
		return this;
	}

	public final Update where(String name, Object value) {
		this.where.where(name, value);
		return this;
	}

	public final Update andWhere(FilterTerm term) {
		this.where.andWhere(term);
		return this;
	}

	public final Update andWhere(FilterExpression expression) {
		this.where.andWhere(expression);
		return this;
	}

	public final Update andWhere(String name, FilterComparison comparison, Object value) {
		this.where.andWhere(name, comparison, value);
		return this;
	}

	public final Update andWhere(String name, Object value) {
		this.where.andWhere(name, value);
		return this;
	}

	public final Update orWhere(FilterTerm term) {
		this.where.orWhere(term);
		return this;
	}

	public final Update orWhere(FilterExpression expression) {
		this.where.orWhere(expression);
		return this;
	}

	public final Update orWhere(String name, FilterComparison comparison, Object value) {
		this.where.orWhere(name, comparison, value);
		return this;
	}

	public final Update orWhere(String name, Object value) {
		this.where.orWhere(name, value);
		return this;
	}

	public abstract long execute();
}
