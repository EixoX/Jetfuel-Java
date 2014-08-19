package com.eixox.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eixox.Convert;
import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntitySelect;
import com.eixox.data.entities.EntitySelectResult;

public final class DatabaseEntitySelect<T> extends EntitySelect<T> {

	public final Database database;

	public DatabaseEntitySelect(EntityAspect aspect, Database database) {
		super(aspect);
		this.database = database;
	}

	@Override
	public final Object readMember(int ordinal) {
		final DatabaseCommand select = this.database.buildSelectMemberCommand(aspect, ordinal, filter, getSort(), pageSize, pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				return select.executeScalar(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final T singleResult() {
		final DatabaseCommand select = this.database.buildSelectCommand(aspect, filter, getSort(), pageSize, pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				return (T) select.executeQuerySingleResult(conn, aspect);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long count() {
		final DatabaseCommand select = this.database.buildSelectCount(aspect, filter);
		try {
			Connection conn = database.getConnection();
			try {
				return Convert.toLong(select.executeScalar(conn));
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean exists() {
		final DatabaseCommand select = this.database.buildSelectExists(aspect, filter);
		try {
			Connection conn = database.getConnection();
			try {
				return Convert.toBoolean(select.executeScalar(conn));
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final EntitySelectResult<T> toResult() {
		final EntitySelectResult<T> result = new EntitySelectResult<T>(count(), pageSize, pageOrdinal);
		final DatabaseCommand select = this.database.buildSelectCommand(aspect, filter, getSort(), pageSize, pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				select.executeQuery(conn, aspect, result.items);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}

	@Override
	public final List<T> toList() {
		final List<T> list = this.pageSize > 0 ? new ArrayList<T>(this.pageSize) : new ArrayList<T>();
		final DatabaseCommand select = this.database.buildSelectCommand(aspect, filter, getSort(), pageSize, pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				select.executeQuery(conn, aspect, list);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

}
