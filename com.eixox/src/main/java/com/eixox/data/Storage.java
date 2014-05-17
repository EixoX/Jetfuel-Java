package com.eixox.data;

import java.util.List;

public abstract class Storage<T> {

	private final DataAspect<?>	aspect;

	public Storage(DataAspect<?> aspect) {
		this.aspect = aspect;
	}

	public final DataAspect<?> getAspect() {
		return this.aspect;
	}

	public final Select<T> select() {
		return new Select<T>(this);
	}

	public final Delete delete() {
		return new Delete(this);
	}

	public final Update update() {
		return new Update(this);
	}

	public final Insert insert() {
		return new Insert(this);
	}

	public synchronized final long updateByMember(T entity, int memberOrdinal, Object memberValue) {
		Update u = new Update(this);
		int s = aspect.getCount();
		for (int i = 0; i < s; i++) {
			if (i != memberOrdinal)
				u.set(i, aspect.getValue(entity, i));
		}
		u.and(memberOrdinal, memberValue);
		return executeUpdate(u);
	}

	public final long updateByMenber(T entity, String memberName, Object memberValue) {
		return updateByMember(entity, aspect.getOrdinalOrException(memberName), memberValue);
	}

	public final long deleteByMember(int memberOrdinal, Object memberValue) {
		return executeDelete(new Delete(this).where(memberOrdinal, memberValue));
	}

	public final long deleteByMember(String memberName, Object memberValue) {
		return deleteByMember(aspect.getOrdinalOrException(memberName), memberValue);
	}

	public abstract void executeSelect(List<T> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	public final void executeSelect(List<T> output, Filter filter, SortExpression sort) {
		executeSelect(output, filter, sort, 0, 0);
	}

	public final void executeSelect(List<T> output, Filter filter) {
		executeSelect(output, filter, null, 0, 0);
	}

	public final SelectResult<T> executeSelect(Select<T> select) {
		final int psize = select.getPageSize();
		final SelectResult<T> sr = new SelectResult<T>(select, (psize > 0 && psize < 100) ? psize : 64);
		executeSelect(sr, select.getFilter(), select.getSort(), psize, select.getPageOrdinal());
		return sr;
	}

	public abstract void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	public final void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter, SortExpression sort) {
		executeSelectMember(memberOrdinal, output, filter, sort, 0, 0);
	}

	public final void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter) {
		executeSelectMember(memberOrdinal, output, filter, null, 0, 0);
	}

	public final SelectMemberResult executeSelectMember(SelectMember select) {
		final int psize = select.getPageSize();
		final SelectMemberResult sr = new SelectMemberResult(select, (psize > 0 && psize < 100) ? psize : 64);
		executeSelectMember(select.getOrdinal(), sr, select.getFilter(), select.getSort(), psize, select.getPageOrdinal());
		return sr;
	}

	public abstract long countWhere(Filter filter);

	public final long countWhere(int ordinal, FilterComparison comparison, Object value) {
		return countWhere(new FilterTerm(aspect, ordinal, comparison, value));
	}

	public final long countWhere(int ordinal, Object value) {
		return countWhere(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public final long countWhere(String name, FilterComparison comparison, Object value) {
		return countWhere(new FilterTerm(aspect, name, comparison, value));
	}

	public final long countWhere(String name, Object value) {
		return countWhere(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));
	}

	public abstract boolean existsWhere(Filter filter);

	public final boolean existsWhere(int ordinal, FilterComparison comparison, Object value) {
		return existsWhere(new FilterTerm(aspect, ordinal, comparison, value));
	}

	public final boolean existsWhere(int ordinal, Object value) {
		return existsWhere(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public final boolean existsWhere(String name, FilterComparison comparison, Object value) {
		return existsWhere(new FilterTerm(aspect, name, comparison, value));
	}

	public final boolean existsWhere(String name, Object value) {
		return existsWhere(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));
	}

	public abstract Object readMemberWhere(int memberOrdinal, Filter filter, SortExpression sort);

	public final Object readMemberWhere(String name, Filter filter, SortExpression sort) {
		return readMemberWhere(aspect.getOrdinalOrException(name), filter, sort);
	}

	public final Object readMemberWhere(int memberOrdinal, Filter filter) {
		return readMemberWhere(memberOrdinal, filter, null);
	}

	public final Object readMemberWhere(String name, Filter filter) {
		return readMemberWhere(aspect.getOrdinalOrException(name), filter, null);
	}

	public final Object readMemberWhere(Select<T> select, int memberOrdinal) {
		return readMemberWhere(memberOrdinal, select.getFilter(), select.getSort());
	}

	public final Object readMemberWhere(Select<T> select, String name) {
		return readMemberWhere(name, select.getFilter(), select.getSort());
	}

	public abstract T readWhere(Filter filter, SortExpression sort);

	public final T readWhere(Filter filter) {
		return readWhere(filter, null);
	}

	public final T readWhere(Select<T> select) {
		return readWhere(select.getFilter(), select.getSort());
	}

	public final T readWhere(String name, FilterComparison comparison, Object value) {
		return readWhere(new FilterTerm(aspect, name, comparison, value));
	}

	public final T readWhere(String name, Object value) {
		return readWhere(new FilterTerm(aspect, name, FilterComparison.EQUAL_TO, value));
	}

	public final T readWhere(int ordinal, FilterComparison comparison, Object value) {
		return readWhere(new FilterTerm(aspect, ordinal, comparison, value));
	}

	public final T readWhere(int ordinal, Object value) {
		return readWhere(new FilterTerm(aspect, ordinal, FilterComparison.EQUAL_TO, value));
	}

	public abstract long executeDelete(Delete delete);

	public abstract long executeUpdate(Update update);

	public abstract long executeInsert(Insert insert);

	public abstract Object executeInsertAndScopeIdentity(Insert insert);

}
