package com.eixox.database;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.eixox.Convert;
import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;
import com.eixox.data.entities.EntityAspect;

public class DatabaseSelect extends DataSelect {

	public final Database database;

	public DatabaseSelect(Database database, String from) {
		super(from);
		this.database = database;
	}

	@Override
	public DataSelectResult toResult() {

		DatabaseCommand cmd = database.dialect.buildSelectCommand(this.from, this.filter, this.sort, this.pageSize, this.pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeQueryToResult(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public long count() {
		DatabaseCommand cmd = database.dialect.buildSelectCountCommand(this.from, this.filter);
		try {
			Connection conn = database.getConnection();
			try {
				return Convert.toLong(cmd.executeScalar(conn));
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean exists() {
		DatabaseCommand cmd = database.dialect.buildSelectExistsCommand(this.from, this.filter);
		try {
			Connection conn = database.getConnection();
			try {
				return Convert.toBoolean(cmd.executeScalar(conn));
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object getFirstMember(String name) {
		DatabaseCommand cmd = database.dialect.buildSelectFirstMemberCommand(this.from, name, this.filter, this.sort);
		try {
			Connection conn = database.getConnection();
			try {
				return Convert.toBoolean(cmd.executeScalar(conn));
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Object> getMember(String name) {
		DatabaseCommand cmd = database.dialect.buildSelectMemberCommand(this.from, name, this.filter, this.sort, this.pageSize, this.pageOrdinal);
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			Connection conn = database.getConnection();
			try {
				cmd.executeMemberQuery(conn, list);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	@Override
	public DataSelectResult getMembers(String... names) {
		DatabaseCommand cmd = database.dialect.buildSelectMembersCommand(this.from, names, this.filter, this.sort, this.pageSize, this.pageOrdinal);

		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeQueryToResult(conn);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public Object[] getFirstMembers(String... names) {
		DatabaseCommand cmd = database.dialect.buildSelectMembersCommand(this.from, names, this.filter, this.sort, 1, 0);
		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeQuerySingleResult(conn, names.length);
			} finally {
				conn.close();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public <T> long transform(EntityAspect aspect, List<T> list) {
		DatabaseCommand cmd = database.dialect.buildSelectCommand(this.from, this.filter, this.sort, this.pageSize, this.pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				return cmd.executeQuery(conn, aspect, list);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final <T> T getEntity(EntityAspect aspect) {
		DatabaseCommand cmd = database.dialect.buildSelectCommand(this.from, this.filter, this.sort, this.pageSize, this.pageOrdinal);
		try {
			Connection conn = database.getConnection();
			try {
				return (T) cmd.executeQuerySingleResult(conn, aspect);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
