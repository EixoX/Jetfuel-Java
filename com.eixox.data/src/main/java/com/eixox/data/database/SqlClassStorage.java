package com.eixox.data.database;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.eixox.data.Adapter;
import com.eixox.data.ClassStorage;
import com.eixox.data.ClassStorageColumn;
import com.eixox.data.Filter;
import com.eixox.data.SortExpression;
import com.eixox.data.ValueAdapters;

public class SqlClassStorage<T> extends ClassStorage<T> {

	private final SqlDatabase database;
	private final SqlDialect dialect;

	public SqlClassStorage(Class<T> dataType, SqlDialect dialect, String url, String username, String password) {
		super(dataType);
		this.dialect = dialect;
		try {
			this.database = new SqlDatabase(url, username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public final SqlDatabase getDatabase() {
		return this.database;
	}

	public final List<T> selectRaw(String command, Object... parameters) {
		try {
			return this.database.executeQuery(command, new Adapter<ResultSet, List<T>>() {
				public List<T> adapt(ResultSet source) {
					try {
						final List<T> list = new ArrayList<T>(32);
						if (source.next()) {
							final ResultSetMetaData metaData = source.getMetaData();
							final int colCount = metaData.getColumnCount();
							final Class<T> claz = getDataType();
							final int[] ordinals = new int[colCount];
							for (int i = 0; i < colCount; i++) {
								ordinals[i] = getOrdinal(metaData.getColumnName(i + 1));
							}
							do {
								T o = claz.newInstance();
								for (int i = 0; i < colCount; i++)
									if (ordinals[i] >= 0) {
										ClassStorageColumn col = get(ordinals[i]);
										Object val = col.readFrom(source, i + 1);
										col.setValue(o, val);
									}
								list.add(o);
							} while (source.next());
						}
						return list;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}
			}, parameters);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final List<T> select(Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {

		final String cmd = this.dialect.buildSelect(this, filter, sort, pageSize, pageOrdinal);

		try {
			return this.database.executeQuery(cmd, new Adapter<ResultSet, List<T>>() {
				public List<T> adapt(ResultSet source) {
					try {
						final List<T> list = new ArrayList<T>(32);
						final int colCount = size();
						final Class<T> claz = getDataType();

						while (source.next()) {
							T o = claz.newInstance();
							for (int i = 0; i < colCount; i++) {
								Object v = get(i).getAdapter().readFrom(source, i + 1);
								setValue(o, i, v);
							}
							list.add(o);
						}
						return list;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			});

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public final List<Object> selectMember(int memberOrdinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		final String cmd = this.dialect.buildSelect(this, filter, sort, pageSize, pageOrdinal);

		try {
			return this.database.executeQuery(cmd, new Adapter<ResultSet, List<Object>>() {
				public List<Object> adapt(ResultSet source) {
					try {
						final List<Object> list = new ArrayList<Object>(32);
						while (source.next())
							list.add(source.getObject(1));
						return list;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			});

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long selectCount(Filter filter) {
		final String cmd = this.dialect.buildSelectCount(this, filter);
		try {
			final Object ret = this.database.executeScalar(cmd);
			return ValueAdapters.LongAdapter.convert(ret);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean selectExists(Filter filter) {
		final String cmd = this.dialect.buildSelectExists(this, filter);
		try {
			final Object ret = this.database.executeScalar(cmd);
			return ValueAdapters.BooleanAdapter.convert(ret);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final T read(Filter filter) {
		final String cmd = this.dialect.buildSelectFirst(this, filter, null);

		try {
			return this.database.executeQuery(cmd, new Adapter<ResultSet, T>() {
				public T adapt(ResultSet source) {
					try {
						if (source.next()) {
							T o = getDataType().newInstance();
							final int colCount = size();
							for (int i = 0; i < colCount; i++) {
								Object v = get(i).getAdapter().readFrom(source, i + 1);
								setValue(o, i, v);
							}
							return o;
						} else
							return null;
					} catch (Exception e) {
						throw new RuntimeException(e);
					}
				}

			});

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final Object readMember(int ordinal, Filter filter) {
		final String cmd = this.dialect.buildSelectFirstMember(this, ordinal, filter, null);
		try {
			return this.database.executeScalar(cmd);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long deleteWhere(Filter filter) {
		final String cmd = this.dialect.buildDelete(this, filter);
		try {
			return this.database.executeNonQuery(cmd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long updateWhere(ArrayList<Integer> ordinals, ArrayList<Object> values, Filter where) {
		final String cmd = this.dialect.buildUpdate(this, ordinals, values, where);
		try {
			return this.database.executeNonQuery(cmd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long insert(ArrayList<Integer> ordinals, ArrayList<Object> values) {
		final String cmd = this.dialect.buildInsert(this, ordinals, values);
		try {
			return this.database.executeNonQuery(cmd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public synchronized Object insertAndScopeIdentity(ArrayList<Integer> ordinals, ArrayList<Object> values) {
		final String cmd = this.dialect.buildInsert(this, ordinals, values);
		try {
			return this.database.executeNonQueryAndScopeIdentity(cmd);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
