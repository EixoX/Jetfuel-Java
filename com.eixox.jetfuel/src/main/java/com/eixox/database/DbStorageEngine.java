package com.eixox.database;

import java.sql.SQLException;
import java.util.Properties;

import com.eixox.Convert;
import com.eixox.PairList;
import com.eixox.data.ClassStorageEngine;
import com.eixox.data.ClassStorage;
import com.eixox.data.SelectResult;
import com.eixox.filters.ClassFilter;
import com.eixox.sorters.ClassSorterNode;

public class DbStorageEngine extends SqlConnection implements ClassStorageEngine {

	private DbDialect dialect;

	// ____________________________________________________________________________________________________________
	public DbStorageEngine(String driverName, String url, Properties properties) throws ClassNotFoundException {
		super(driverName, url, properties);
		this.dialect = new DbDialect();
	}

	// ____________________________________________________________________________________________________________
	public DbStorageEngine(String driverName, String url, String username, String password) throws ClassNotFoundException {
		super(driverName, url, username, password);
		this.dialect = new DbDialect();
	}

	// ____________________________________________________________________________________________________________
	public final DbDialect getDialect() {
		return this.dialect;
	}

	// ____________________________________________________________________________________________________________
	public final void setDialect(DbDialect dialect) {
		this.dialect = dialect;
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final <T> SelectResult<T> select(ClassStorage<T> aspect, ClassFilter filter, ClassSorterNode sorter, int pageSize, int pageOrdinal) {

		SqlCommand cmd = this.dialect.createSelect(aspect, filter, sorter, pageSize, pageOrdinal);
		DbStorageProcessorForClass<T> processor = new DbStorageProcessorForClass<T>(aspect, pageSize, pageOrdinal);

		cmd.setConnection(this);
		try {
			return cmd.executeQuery(processor);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final <T> SelectResult<T> selectByMember(ClassStorage<T> aspect, int filterOrdinal, Object filterValue, int pageSize, int pageOrdinal) {

		SqlCommand cmd = this.dialect.createSelectByMember(aspect, filterOrdinal, filterValue, pageSize, pageOrdinal);
		DbStorageProcessorForClass<T> processor = new DbStorageProcessorForClass<T>(aspect, pageSize, pageOrdinal);

		cmd.setConnection(this);
		try {
			return cmd.executeQuery(processor);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final <T> SelectResult<T> selectByMembers(ClassStorage<T> aspect, int[] filterOrdinals, Object[] filterValues, int pageSize, int pageOrdinal) {

		SqlCommand cmd = this.dialect.createSelectByMembers(aspect, filterOrdinals, filterValues, pageSize, pageOrdinal);
		DbStorageProcessorForClass<T> processor = new DbStorageProcessorForClass<T>(aspect, pageSize, pageOrdinal);

		cmd.setConnection(this);
		try {
			return cmd.executeQuery(processor);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long delete(ClassStorage<?> aspect, ClassFilter filter) {

		SqlCommand cmd = this.dialect.createDelete(aspect, filter);

		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long deleteByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue) {

		SqlCommand cmd = this.dialect.createDeleteByMember(aspect, memberOrdinal, memberValue);

		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long deleteByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues) {

		SqlCommand cmd = this.dialect.createDeleteByMembers(aspect, memberOrdinals, memberValues);

		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long insert(ClassStorage<?> aspect, PairList<Integer, Object> values) {

		SqlCommand cmd = this.dialect.createInsert(aspect, values);
		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final Object insertAndScopeIdentity(ClassStorage<?> aspect, PairList<Integer, Object> values, int identityOrdinal) {

		SqlCommand cmd = this.dialect.createInsertAndScopeIdentity(aspect, values, identityOrdinal);
		cmd.setConnection(this);
		try {
			return cmd.executeGenerated();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long update(ClassStorage<?> aspect, PairList<Integer, Object> values, ClassFilter filter) {

		SqlCommand cmd = this.dialect.createUpdate(aspect, values, filter);
		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long updateByMember(ClassStorage<?> aspect, PairList<Integer, Object> values, int filterOrdinal, Object filterValue) {

		SqlCommand cmd = this.dialect.createUpdateByMember(aspect, values, filterOrdinal, filterValue);
		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long updateByMembers(ClassStorage<?> aspect, PairList<Integer, Object> values, int[] filterOrdinals, Object[] filterValues) {

		SqlCommand cmd = this.dialect.createUpdateByMembers(aspect, values, filterOrdinals, filterValues);

		cmd.setConnection(this);
		try {
			return cmd.executeNonQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@SuppressWarnings("unchecked")
	@Override
	public final <T> T selectOne(ClassStorage<T> aspect, ClassFilter filter, ClassSorterNode sorter) {

		SqlCommand cmd = this.dialect.createSelectOne(aspect, filter, sorter);
		cmd.setConnection(this);

		try {
			return (T) cmd.executeScalar(aspect);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@SuppressWarnings("unchecked")
	@Override
	public final <T> T selectOneByMember(ClassStorage<T> aspect, int memberOrdinal, Object memberValue) {

		SqlCommand cmd = this.dialect.createSelectOneByMember(aspect, memberOrdinal, memberValue);
		cmd.setConnection(this);

		try {
			return (T) cmd.executeScalar(aspect);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@SuppressWarnings("unchecked")
	@Override
	public final <T> T selectOneByMembers(ClassStorage<T> aspect, int[] memberOrdinals, Object[] memberValues) {

		SqlCommand cmd = this.dialect.createSelectOneByMembers(aspect, memberOrdinals, memberValues);
		cmd.setConnection(this);

		try {
			return (T) cmd.executeScalar(aspect);
		} catch (InstantiationException | IllegalAccessException | SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final Object selectMember(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter, ClassSorterNode sorter) {

		SqlCommand cmd = this.dialect.createSelectMember(aspect, memberOrdinal, filter, sorter);
		cmd.setConnection(this);

		try {
			return cmd.executeScalar();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final Object selectMemberByMember(ClassStorage<?> aspect, int memberOrdinal, int filterOrdinal, Object filterValue) {

		SqlCommand cmd = this.dialect.createSelectMemberByMember(aspect, memberOrdinal, filterOrdinal, filterValue);
		cmd.setConnection(this);

		try {
			return cmd.executeScalar();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final Object selectMemberByMembers(ClassStorage<?> aspect, int memberOrdinal, int[] filterOrdinals, Object[] filterValues) {

		SqlCommand cmd = this.dialect.createSelectMemberByMembers(aspect, memberOrdinal, filterOrdinals, filterValues);
		cmd.setConnection(this);

		try {
			return cmd.executeScalar();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ____________________________________________________________________________________________________________
	@Override
	public final SelectResult<Object> selectMembers(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter, ClassSorterNode sorter, int pageSize,
			int pageOrdinal) {

		SqlCommand cmd = this.dialect.createSelectMembers(aspect, memberOrdinal, filter, sorter, pageSize, pageOrdinal);
		DbStorageProcessorForColumn processor = new DbStorageProcessorForColumn(1, pageSize, pageOrdinal);
		try {
			return cmd.executeQuery(processor);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long selectCount(ClassStorage<?> aspect, ClassFilter filter) {

		SqlCommand cmd = this.dialect.createSelectCount(aspect, filter);
		cmd.setConnection(this);

		try {
			return Convert.toLong(cmd.executeScalar());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long selectCountByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue) {

		SqlCommand cmd = this.dialect.createSelectCountByMember(aspect, memberOrdinal, memberValue);
		cmd.setConnection(this);

		try {
			return Convert.toLong(cmd.executeScalar());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	// ____________________________________________________________________________________________________________
	@Override
	public final long selectCountByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues) {

		SqlCommand cmd = this.dialect.createSelectCountByMembers(aspect, memberOrdinals, memberValues);
		cmd.setConnection(this);

		try {
			return Convert.toLong(cmd.executeScalar());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

}
