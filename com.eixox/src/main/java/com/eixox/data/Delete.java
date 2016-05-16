package com.eixox.data;

public abstract class Delete implements Filterable<Delete> {

	public final FilterExpression where = new FilterExpression();;

	public final Delete where(FilterTerm term) {
		this.where.where(term);
		return this;
	}

	public final Delete where(FilterExpression expression) {
		this.where.where(expression);
		return this;
	}

	public final Delete where(String name, FilterComparison comparison, Object value) {
		this.where.where(name, comparison, value);
		return this;
	}

	public final Delete where(String name, Object value) {
		this.where.where(name, value);
		return this;
	}

	public final Delete andWhere(FilterTerm term) {
		this.where.andWhere(term);
		return this;
	}

	public final Delete andWhere(FilterExpression expression) {
		this.where.andWhere(expression);
		return this;
	}

	public final Delete andWhere(String name, FilterComparison comparison, Object value) {
		this.where.andWhere(name, comparison, value);
		return this;
	}

	public final Delete andWhere(String name, Object value) {
		this.where.andWhere(name, value);
		return this;
	}

	public final Delete orWhere(FilterTerm term) {
		this.where.orWhere(term);
		return this;
	}

	public final Delete orWhere(FilterExpression expression) {
		this.where.orWhere(expression);
		return this;
	}

	public final Delete orWhere(String name, FilterComparison comparison, Object value) {
		this.where.orWhere(name, comparison, value);
		return this;
	}

	public final Delete orWhere(String name, Object value) {
		this.where.orWhere(name, value);
		return this;
	}

	public abstract long execute();

}
