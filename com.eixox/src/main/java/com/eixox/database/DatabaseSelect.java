package com.eixox.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

import com.eixox.data.Select;
import com.eixox.data.SelectAggregate;
import com.eixox.data.SelectResult;
import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;

public class DatabaseSelect extends Select {

	public final Database database;
	public final String tableName;

	public DatabaseSelect(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	@Override
	public SelectResult toResult() {
		try {
			Connection conn = this.database.createConnection();
			try {
				return toResult(conn);
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public SelectResult toResult(Connection conn) throws SQLException {
		return this.database.createCommand()
				.appendSql("SELECT ")
				.appendTop(offset)
				.appendNames(columns, aggregates)
				.appendSql(" FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.appendGroupBy(aggregates.size() > 0 ? columns : null)
				.appendHaving(having)
				.appendOrderBy(sort)
				.appendOffset(offset)
				.appendLimit(limit)
				.executeQuery(conn, new DatabaseCommandProcessor<SelectResult>() {

					public SelectResult process(ResultSet rs) throws SQLException {
						SelectResult result = new SelectResult(columns, limit > 0 ? limit : 10);
						for (SelectAggregate aggr : aggregates)
							result.columns.add(aggr.getCaption());
						while (rs.next()) {
							Object[] row = new Object[result.columns.size()];
							for (int i = 0; i < row.length; i++)
								row[i] = rs.getObject(i + 1);
							result.rows.add(row);
						}
						return result;
					}

				});
	}

	@Override
	public long count() {
		try {
			Connection conn = database.createConnection();
			try {
				return count(conn);
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public long count(Connection connection) throws SQLException {
		Object val = this.database.createCommand()
				.appendSql("SELECT COUNT(*) FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.executeScalar(connection);

		if (val == null)
			return 0L;
		else
			return ((Number) val).longValue();
	}

	public boolean exists(Connection connection) throws SQLException {
		Object val = this.database.createCommand()
				.appendSql("SELECT 1 as one FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.executeScalar(connection);

		if (val == null)
			return false;
		else
			return ((Number) val).intValue() == 1;
	}

	@Override
	public boolean exists() {
		try {
			Connection conn = database.createConnection();
			try {
				return exists(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object[] first(Connection conn) throws SQLException {
		return this.database.createCommand()
				.appendSql("SELECT ")
				.appendTop(1)
				.appendNames(columns, aggregates)
				.appendSql(" FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.appendGroupBy(aggregates.size() > 0 ? columns : null)
				.appendHaving(having)
				.appendOrderBy(sort)
				.appendLimit(1)
				.executeQuery(conn, new DatabaseCommandProcessor<Object[]>() {
					public Object[] process(ResultSet rs) throws SQLException {
						if (!rs.next())
							return null;
						int s = columns.size() + aggregates.size();
						Object[] row = new Object[s];
						for (int i = 0; i < row.length; i++)
							row[i] = rs.getObject(i + 1);
						return row;
					}
				});
	}

	@Override
	public Object[] first() {
		try {
			Connection conn = this.database.createConnection();
			try {
				return first(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object scalar(Connection conn) throws SQLException {
		return this.database.createCommand()
				.appendSql("SELECT ")
				.appendTop(1)
				.appendNames(columns, aggregates)
				.appendSql(" FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.appendGroupBy(aggregates.size() > 0 ? columns : null)
				.appendHaving(having)
				.appendOrderBy(sort)
				.appendLimit(1)
				.executeQuery(conn, new DatabaseCommandProcessor<Object>() {
					public Object process(ResultSet rs) throws SQLException {
						return rs.next() ? rs.getObject(1) : null;
					}
				});
	}

	@Override
	public Object scalar() {
		try {
			Connection conn = this.database.createConnection();
			try {
				return scalar(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public <T> int transform(Connection conn, final EntityAspect<T> aspect, final List<T> target) throws SQLException {
		return this.database.createCommand()
				.appendSql("SELECT ")
				.appendTop(offset)
				.appendNames(columns, aggregates)
				.appendSql(" FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.appendGroupBy(aggregates.size() > 0 ? columns : null)
				.appendHaving(having)
				.appendOrderBy(sort)
				.appendOffset(offset)
				.appendLimit(limit)
				.executeQuery(conn, new DatabaseCommandProcessor<Integer>() {
					@SuppressWarnings("unchecked")
					public Integer process(ResultSet rs) throws SQLException {
						int counter = 0;
						ResultSetMetaData metaData = rs.getMetaData();
						EntityAspectMember<T>[] members = new EntityAspectMember[metaData.getColumnCount()];
						for (int i = 0; i < members.length; i++) {
							members[i] = aspect.getByColumnName(metaData.getColumnName(i + 1));
						}
						while (rs.next()) {
							T entity = aspect.newInstance();
							for (int i = 0; i < members.length; i++) {
								if (members[i] != null) {
									Object val = rs.getObject(i + 1);
									if (val != null)
										members[i].setValue(entity, val);
								}
							}
							target.add(entity);
							counter++;
						}
						return counter;
					}
				});
	}

	@Override
	public <T> int transform(EntityAspect<T> aspect, List<T> target) {
		try {
			Connection conn = this.database.createConnection();
			try {
				return transform(conn, aspect, target);
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public <T> T first(Connection conn, final EntityAspect<T> aspect) throws SQLException {
		return this.database.createCommand()
				.appendSql("SELECT ")
				.appendTop(1)
				.appendNames(columns, aggregates)
				.appendSql(" FROM ")
				.appendName(tableName)
				.appendWhere(filter)
				.appendGroupBy(aggregates.size() > 0 ? columns : null)
				.appendHaving(having)
				.appendOrderBy(sort)
				.appendLimit(1)
				.executeQuery(conn, new DatabaseCommandProcessor<T>() {
					public T process(ResultSet rs) throws SQLException {
						if (!rs.next())
							return null;
						T entity = aspect.newInstance();
						for (EntityAspectMember<T> member : aspect) {
							Object val = rs.getObject(member.columName);
							if (val != null)
								member.setValue(entity, val);
						}
						return entity;
					}
				});
	}

	@Override
	public <T> T first(EntityAspect<T> aspect) {
		try {
			Connection conn = this.database.createConnection();
			try {
				return first(conn, aspect);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
