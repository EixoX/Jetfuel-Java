package com.eixox.data;

import java.util.List;

public abstract class Storage<T> {

	private final DataAspect	aspect;

	public Storage(Class<T> claz) {
		this.aspect = DataAspect.getInstance(claz);
	}

	public final DataAspect getAspect() {
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

	public synchronized final long update(T entity) {
		if (aspect.hasIdentity()) {
			int identityOrdinal = aspect.getIdentityOrdinal();
			if (identityOrdinal >= 0) {
				DataAspectMember identity = aspect.get(identityOrdinal);
				Object identityValue = identity.getValue(entity);
				if (!identity.getValueAdapter().IsNullOrEmpty(identityValue)) {
					return updateByMember(entity, identityOrdinal, identityValue);
				}
			}
		}
		if (aspect.hasUniqueOrdinals()) {
			int[] uniqueOrdinals = aspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null) {
					return updateByMember(entity, uniqueOrdinals[i], uniqueValue);
				}
			}
		}
		if (aspect.hasPrimaryKeys()) {
			Update u = new Update(this);
			int s = aspect.getCount();
			int identityOrdinal = aspect.getIdentityOrdinal();
			for (int i = 0; i < s; i++)
				if (aspect.isPrimaryKey(i))
					u.and(i, aspect.getValue(entity, i));
				else if (i != identityOrdinal)
					u.set(i, aspect.getValue(entity, i));

			return executeUpdate(u);
		}

		throw new RuntimeException("Unable to find ways to update " + entity);
	}

	public synchronized final Object findIdentityValue(T entity) {
		int identityOrdinal = aspect.getIdentityOrdinal();
		if (identityOrdinal < 0)
			throw new RuntimeException(entity + " has no identity annotation.");
		Object identityValue = aspect.getValue(entity, identityOrdinal);
		if (aspect.get(identityOrdinal).getValueAdapter().IsNullOrEmpty(identityValue)) {
			if (aspect.hasUniqueOrdinals()) {
				int[] uniqueOrdinals = aspect.getUniqueOrdinals();
				for (int i = 0; i < uniqueOrdinals.length; i++)
				{
					Object uniqueValue = aspect.getValue(entity, uniqueOrdinals[i]);
					if (uniqueValue != null)
					{
						identityValue = readMemberWhere(identityOrdinal, new FilterTerm(aspect, uniqueOrdinals[i], FilterComparison.EQUAL_TO, uniqueValue));
						if (identityValue != null)
							return identityValue;
					}
				}
			}
			if (aspect.hasPrimaryKeys()) {
				int[] pks = aspect.getPrimaryKeyOrdinals();
				FilterExpression exp = new FilterExpression(new FilterTerm(aspect, pks[0], FilterComparison.EQUAL_TO, aspect.getValue(entity, pks[0])));
				for (int i = 1; i < pks.length; i++)
					exp.and(pks[i], aspect.getValue(entity, pks[i]));
				return readMemberWhere(identityOrdinal, exp);
			}
			else
				return null;
		}
		else
			return identityValue;

	}

	public synchronized final long updateByMember(T entity, int memberOrdinal, Object memberValue) {
		int identityOrdinal = aspect.getIdentityOrdinal();
		Update u = new Update(this);
		int s = aspect.getCount();
		for (int i = 0; i < s; i++) {
			if (i != memberOrdinal && i != identityOrdinal)
				u.set(i, aspect.getValue(entity, i));
		}
		u.and(memberOrdinal, memberValue);
		return executeUpdate(u);
	}

	public final long updateByMenber(T entity, String memberName, Object memberValue) {
		return updateByMember(entity, aspect.getOrdinalOrException(memberName), memberValue);
	}

	public synchronized final long delete(T entity) {
		if (aspect.hasIdentity()) {
			int identityOrdinal = aspect.getIdentityOrdinal();
			if (identityOrdinal >= 0) {
				DataAspectMember identity = aspect.get(identityOrdinal);
				Object identityValue = identity.getValue(entity);
				if (!identity.getValueAdapter().IsNullOrEmpty(identityValue)) {
					return executeDelete(new Delete(this).where(identityOrdinal, identityValue));
				}
			}
		}
		if (aspect.hasUniqueOrdinals()) {
			int[] uniqueOrdinals = aspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null) {
					return executeDelete(new Delete(this).where(uniqueOrdinals[i], uniqueValue));
				}
			}
		}
		if (aspect.hasPrimaryKeys()) {
			Delete d = new Delete(this);
			int[] pks = aspect.getPrimaryKeyOrdinals();
			for (int i = 0; i < pks.length; i++)
				d.and(pks[i], aspect.getValue(entity, pks[i]));
			return executeDelete(d);
		}

		throw new RuntimeException("Unable to find ways to delete " + entity);
	}

	public synchronized final Object insert(T entity) {
		int identityOrdinal = aspect.getIdentityOrdinal();
		Insert insert = new Insert(this);
		int s = aspect.getCount();
		for (int i = 0; i < s; i++)
			if (i != identityOrdinal)
				insert.add(i, aspect.getValue(entity, i));
		if (identityOrdinal >= 0) {
			Object identityValue = executeInsertAndScopeIdentity(insert);
			aspect.setValue(entity, identityOrdinal, identityValue);
			return identityValue;
		}
		else {
			executeInsert(insert);
			return null;
		}
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

	public synchronized final boolean exists(T entity) {
		if (aspect.hasIdentity()) {
			int identityOrdinal = aspect.getIdentityOrdinal();
			DataAspectMember identity = aspect.get(identityOrdinal);
			Object identityValue = identity.getValue(entity);
			if (!identity.getValueAdapter().IsNullOrEmpty(identityValue))
				return existsWhere(identityOrdinal, identityValue);
		}
		if (aspect.hasUniqueOrdinals()) {
			int[] uniqueOrdinals = aspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++)
			{
				Object uniqueValue = aspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null)
					if (existsWhere(uniqueOrdinals[i], uniqueValue))
						return true;
			}
		}
		if (aspect.hasPrimaryKeys()) {
			int[] pks = aspect.getPrimaryKeyOrdinals();
			FilterExpression expression = new FilterExpression(new FilterTerm(aspect, pks[0], FilterComparison.EQUAL_TO, aspect.getValue(entity, pks[0])));
			for (int i = 1; i < pks.length; i++)
				expression.and(pks[i], aspect.getValue(entity, pks[i]));
			return existsWhere(expression);
		}

		throw new RuntimeException("Unable to find ways to test if exists " + entity);
	}

	public synchronized final void save(T entity) {
		if (aspect.hasIdentity()) {
			Object identityValue = this.findIdentityValue(entity);
			if (identityValue != null)
			{
				aspect.setValue(entity, aspect.getIdentityOrdinal(), identityValue);
				updateByMember(entity, aspect.getIdentityOrdinal(), identityValue);
			}
			else
				insert(entity);
		}
		else {
			if (aspect.hasUniqueOrdinals()) {
				int[] uniqueOrdinals = aspect.getUniqueOrdinals();
				for (int i = 0; i < uniqueOrdinals.length; i++)
				{
					Object uniqueValue = aspect.getValue(entity, uniqueOrdinals[i]);
					if (uniqueValue != null)
						if (existsWhere(uniqueOrdinals[i], uniqueValue))
						{
							updateByMember(entity, uniqueOrdinals[i], uniqueValue);
							return;
						}
						else {
							insert(entity);
							return;
						}
				}
			}
			if (aspect.hasPrimaryKeys()) {
				int[] pks = aspect.getPrimaryKeyOrdinals();
				FilterExpression expression = new FilterExpression(new FilterTerm(aspect, pks[0], FilterComparison.EQUAL_TO, aspect.getValue(entity, pks[0])));
				for (int i = 1; i < pks.length; i++)
					expression.and(pks[i], aspect.getValue(entity, pks[i]));
				if (existsWhere(expression))
				{
					Update u = new Update(this);
					int s = aspect.getCount();
					int identityOrdinal = aspect.getIdentityOrdinal();
					for (int i = 0; i < s; i++)
						if (aspect.isPrimaryKey(i))
							u.and(i, aspect.getValue(entity, i));
						else if (i != identityOrdinal)
							u.set(i, aspect.getValue(entity, i));

					executeUpdate(u);
					return;
				}
			}
			else
				insert(entity);
		}
	}

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

	public final T readByIdentity(Object identityValue) {
		final int identityOrdinal = aspect.getIdentityOrdinal();
		if (identityOrdinal < 0)
			throw new RuntimeException(aspect.getDataType() + " has no identity set.");
		else if (aspect.get(identityOrdinal).getValueAdapter().IsNullOrEmpty(identityValue))
			return null;
		else
			return readWhere(new FilterTerm(aspect, identityOrdinal, FilterComparison.EQUAL_TO, identityValue));
	}

	public abstract long executeDelete(Delete delete);

	public abstract long executeUpdate(Update update);

	public abstract long executeInsert(Insert insert);

	public abstract Object executeInsertAndScopeIdentity(Insert insert);

}
