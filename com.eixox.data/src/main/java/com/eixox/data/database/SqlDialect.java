package com.eixox.data.database;

import java.util.List;

import com.eixox.data.Filter;
import com.eixox.data.SortExpression;

public interface SqlDialect {

	public String buildSelect(SqlClassStorage<?> storage, Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	public String buildSelectMember(SqlClassStorage<?> storage, int ordinal, Filter filter, SortExpression sort, int pageSize, int pageOrdinal);

	public String buildSelectFirst(SqlClassStorage<?> storage, Filter filter, SortExpression sort);

	public String buildSelectFirstMember(SqlClassStorage<?> storage, int ordinal, Filter filter, SortExpression sort);

	public String buildSelectCount(SqlClassStorage<?> storage, Filter filter);

	public String buildDelete(SqlClassStorage<?> storage, Filter filter);

	public String buildUpdate(SqlClassStorage<?> storage, List<Integer> ordinals, List<Object> values, Filter filter);

	public String buildInsert(SqlClassStorage<?> storage, List<Integer> ordinals, List<Object> values);

	public String buildSelectExists(SqlClassStorage<?> storage, Filter filter);

}
