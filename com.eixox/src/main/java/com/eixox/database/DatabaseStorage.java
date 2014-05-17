package com.eixox.database;

import java.util.List;
import java.util.Properties;

import com.eixox.Convert;
import com.eixox.adapters.ValueAdapter;
import com.eixox.data.Delete;
import com.eixox.data.Filter;
import com.eixox.data.FilterComparison;
import com.eixox.data.FilterTerm;
import com.eixox.data.Insert;
import com.eixox.data.SortExpression;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class DatabaseStorage<T> extends Storage<T> {

	private final DatabaseEngine engine;
	private final DatabaseDialect dialect;
	private final DatabaseAspect databaseAspect;

	public DatabaseStorage(Class<T> claz, String connectionString, Properties properties, DatabaseDialect dialect) {
		super(DatabaseAspect.getInstance(claz));
		this.databaseAspect = (DatabaseAspect) super.getAspect();
		this.engine = new DatabaseEngine(connectionString, properties);
		this.dialect = dialect;
	}

	public final DatabaseEngine getEngine() {
		return this.engine;
	}

	public final DatabaseDialect getDialect() {
		return this.dialect;
	}

	public final DatabaseAspect getDatabaseAspect() {
		return this.databaseAspect;
	}

	@Override
	public final synchronized long executeDelete(Delete delete) {
		final String cmd = this.dialect.buildDeleteCommand((DatabaseAspect) getAspect(), delete.getFilter());
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized long executeUpdate(Update update) {
		final String cmd = this.dialect.buildUpdateCommand((DatabaseAspect) getAspect(), update.getValues(), update.getFilter());
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized long executeInsert(Insert insert) {
		final String cmd = this.dialect.buildInsertCommand((DatabaseAspect) getAspect(), insert);
		final Object result = this.engine.executeNonQuery(cmd);
		return Convert.toLong(result);
	}

	@Override
	public final synchronized Object executeInsertAndScopeIdentity(Insert insert) {
		final String cmd = this.dialect.buildInsertCommand((DatabaseAspect) getAspect(), insert);
		return this.engine.executeNonQueryAndScopeIdentity(cmd);
	}

	@Override
	public final synchronized void executeSelect(List<T> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final DatabaseAspect aspect = (DatabaseAspect) getAspect();
		final String cmd = this.dialect.buildSelectCommand(aspect, filter, sort, pageSize, pageOrdinal);
		this.engine.executeQuery(output, aspect, cmd);
	}

	@Override
	public final synchronized void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final DatabaseAspect aspect = (DatabaseAspect) getAspect();
		final String cmd = this.dialect.buildSelectMemberCommand(aspect, memberOrdinal, filter, sort, pageSize, pageOrdinal);
		this.engine.executeQuery(output, aspect, cmd);
	}

	@Override
	public final synchronized long countWhere(Filter filter) {
		final String cmd = this.dialect.buildSelectCount((DatabaseAspect) getAspect(), filter);
		final Object o = engine.executeScalar(cmd);
		return Convert.toLong(o);
	}

	@Override
	public final synchronized boolean existsWhere(Filter filter) {
		final String cmd = this.dialect.buildSelectExists((DatabaseAspect) getAspect(), filter);
		final Object o = engine.executeScalar(cmd);
		return Convert.toBoolean(o);
	}

	@Override
	public final synchronized Object readMemberWhere(int memberOrdinal, Filter filter, SortExpression sort) {
		final DatabaseAspect aspect = (DatabaseAspect) getAspect();
		final String cmd = this.dialect.buildSelectMemberCommand(aspect, memberOrdinal, filter, sort, 1, 0);
		return this.engine.executeScalar(cmd);
	}

	@Override
	public final synchronized T readWhere(Filter filter, SortExpression sort) {
		final String cmd = this.dialect.buildSelectCommand((DatabaseAspect) getAspect(), filter, sort, 1, 0);
		return this.engine.executeEntity((DatabaseAspect) getAspect(), cmd);
	}

	public final T readByIdentity(Object id) {
		if (!this.databaseAspect.hasIdentity())
			throw new RuntimeException(this.databaseAspect.getDataType() + " has no identity.");
		else
			return readWhere(new FilterTerm(this.databaseAspect, this.databaseAspect.getIdentityOrdinal(), FilterComparison.EQUAL_TO, id));
	}

	public final synchronized boolean delete(T entity) {
		if (databaseAspect.hasIdentity()) {
			final int identityOrdinal = databaseAspect.getIdentityOrdinal();
			final DatabaseAspectMember dam = this.databaseAspect.get(identityOrdinal);
			final Object identityValue = dam.getValue(entity);
			final ValueAdapter<?> valueAdapter = dam.getValueAdapter();
			if (!valueAdapter.IsNullOrEmpty(identityValue))
				return deleteByMember(identityOrdinal, identityValue) > 0;
		}
		if (databaseAspect.hasUniqueOrdinals()) {
			final int[] uniqueOrdinals = this.databaseAspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				final Object uniqueValue = this.databaseAspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null)
					if (deleteByMember(uniqueOrdinals[i], uniqueValue) > 0)
						return true;
			}
		}
		if (databaseAspect.hasPrimaryKeys()) {
			final Filter pkFilter = this.databaseAspect.createPrimaryKeyFilter(entity);
			return executeDelete(new Delete(this).where(pkFilter)) > 0;
		}

		return false;
	}

	public final synchronized boolean locateIdentity(T entity) {
		final int identityOrdinal = this.databaseAspect.getIdentityOrdinal();
		if (identityOrdinal < 0)
			return false;
		final DatabaseAspectMember identity = this.databaseAspect.get(identityOrdinal);
		final ValueAdapter<?> adapter = identity.getValueAdapter();
		Object identityValue = this.databaseAspect.getValue(entity, identityOrdinal);
		if (!adapter.IsNullOrEmpty(identityValue))
			return true;
		if (this.databaseAspect.hasUniqueOrdinals()) {
			final int[] uniqueOrdinals = this.databaseAspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				final Object uniqueValue = this.databaseAspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null) {
					identityValue = readMemberWhere(identityOrdinal, new FilterTerm(this.databaseAspect, uniqueOrdinals[i], FilterComparison.EQUAL_TO, uniqueValue));
					if (identityValue != null) {
						identity.setDataValue(entity, identityValue);
						return true;
					}
				}
			}
		}
		if (this.databaseAspect.hasPrimaryKeys()) {
			identityValue = readMemberWhere(identityOrdinal, this.databaseAspect.createPrimaryKeyFilter(entity));
			if (identityValue != null) {
				identity.setDataValue(entity, identityValue);
				return true;
			}
		}
		return false;
	}

	public final synchronized boolean insert(T entity) {
		final int identityOrdinal = this.databaseAspect.getIdentityOrdinal();
		final int count = this.databaseAspect.getCount();
		final Insert ins = new Insert(this);
		for (int i = 0; i < count; i++)
			if (i != identityOrdinal)
				ins.add(i, this.databaseAspect.getValue(entity, i));

		if (identityOrdinal < 0) {
			return ins.execute() > 0;
		} else {
			final Object identityValue = ins.executeAndScopeIdentity();
			if (identityValue == null)
				return false;
			else {
				this.databaseAspect.setDataValue(entity, identityOrdinal, identityValue);
				return true;
			}
		}
	}

	public final synchronized boolean update(T entity) {
		final int identityOrdinal = databaseAspect.getIdentityOrdinal();
		if (databaseAspect.hasIdentity()) {
			final DatabaseAspectMember identity = databaseAspect.getIdentity();
			final ValueAdapter<?> adapter = identity.getValueAdapter();
			final Object identityValue = identity.getValue(entity);
			if (!adapter.IsNullOrEmpty(identityValue))
				return updateByMember(entity, identityOrdinal, identityValue) > 0;
			else
				throw new RuntimeException("Cannot update an entity when the identity is not set (" + entity + "). Use the save method if you want to update using all available means");
		}
		if (databaseAspect.hasUniqueOrdinals()) {
			final int[] uniqueOrdinals = databaseAspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				final Object uniqueValue = databaseAspect.getValue(entity, uniqueOrdinals[i]);
				if (uniqueValue != null)
					return updateByMember(entity, uniqueOrdinals[i], uniqueValue) > 0;
			}
		}
		if (databaseAspect.hasPrimaryKeys()) {
			final int count = this.databaseAspect.getCount();
			final Update up = new Update(this);
			for (int i = 0; i < count; i++)
				if (this.databaseAspect.isPrimaryKey(i)) {
					up.and(i, this.databaseAspect.getValue(entity, i));
				} else if (i != identityOrdinal) {
					up.set(i, this.databaseAspect.getValue(entity, i));
				}
			return up.execute() > 0;
		}
		return false;
	}

	public final boolean save(T entity) {
		if (databaseAspect.hasIdentity()) {
			if (locateIdentity(entity))
				return updateByMember(entity, databaseAspect.getIdentityOrdinal(), databaseAspect.getIdentityValue(entity)) > 0;
			else
				return insert(entity);
		}
		if (databaseAspect.hasUniqueOrdinals()) {
			final int[] uniqueOrdinals = this.databaseAspect.getUniqueOrdinals();
			for (int i = 0; i < uniqueOrdinals.length; i++) {
				final DatabaseAspectMember uniqueMember = this.databaseAspect.get(uniqueOrdinals[i]);
				final Object uniqueValue = uniqueMember.getValue(entity);
				if (!uniqueMember.getValueAdapter().IsNullOrEmpty(uniqueValue))
					if (existsWhere(uniqueOrdinals[i], uniqueValue))
						return updateByMember(entity, uniqueOrdinals[i], uniqueValue) > 0;
			}
		}
		if (databaseAspect.hasPrimaryKeys()) {
			final Filter pkFilter = databaseAspect.createPrimaryKeyFilter(entity);
			if (existsWhere(pkFilter)) {
				final int count = this.databaseAspect.getCount();
				final int identityOrdinal = this.databaseAspect.getIdentityOrdinal();
				final Update up = new Update(this).where(pkFilter);
				for (int i = 0; i < count; i++)
					if (i != identityOrdinal && !this.databaseAspect.isPrimaryKey(i))
						up.set(i, this.databaseAspect.getValue(entity, i));
				return up.execute() > 0;
			}
		}

		return insert(entity);
	}

	public final int insertBulk(Iterable<T> items) {
		final String cmd = this.dialect.buildInsertBulk(databaseAspect, items);
		return this.engine.executeNonQuery(cmd);
	}
}
