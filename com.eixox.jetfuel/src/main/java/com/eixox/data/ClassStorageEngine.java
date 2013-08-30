package com.eixox.data;

import com.eixox.PairList;
import com.eixox.filters.ClassFilter;
import com.eixox.sorters.ClassSorterNode;

public interface ClassStorageEngine {

	public <T> SelectResult<T> select(ClassStorage<T> aspect, ClassFilter filter, ClassSorterNode sorter, int pageSize,
			int pageOrdinal);

	public <T> SelectResult<T> selectByMember(ClassStorage<T> aspect, int filterOrdinal, Object filterValue,
			int pageSize, int pageOrdinal);

	public <T> SelectResult<T> selectByMembers(ClassStorage<T> aspect, int[] filterOrdinals, Object[] filterValues,
			int pageSize, int pageOrdinal);

	public long delete(ClassStorage<?> aspect, ClassFilter filter);

	public long deleteByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue);

	public long deleteByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues);

	public long insert(ClassStorage<?> aspect, PairList<Integer, Object> values);

	public Object insertAndScopeIdentity(ClassStorage<?> aspect, PairList<Integer, Object> values, int identityOrdinal);

	public long update(ClassStorage<?> aspect, PairList<Integer, Object> values, ClassFilter filter);

	public long updateByMember(ClassStorage<?> aspect, PairList<Integer, Object> values, int filterOrdinal,
			Object filterValue);

	public long updateByMembers(ClassStorage<?> aspect, PairList<Integer, Object> values, int[] filterOrdinals,
			Object[] filterValues);

	public <T> T selectOne(ClassStorage<T> aspect, ClassFilter filter, ClassSorterNode sorter);

	public <T> T selectOneByMember(ClassStorage<T> aspect, int memberOrdinal, Object memberValue);

	public <T> T selectOneByMembers(ClassStorage<T> aspect, int[] memberOrdinals, Object[] memberValues);

	public Object selectMember(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter, ClassSorterNode sorter);

	public Object selectMemberByMember(ClassStorage<?> aspect, int memberOrdinal, int filterOrdinal, Object filterValue);

	public Object selectMemberByMembers(ClassStorage<?> aspect, int memberOrdinal, int[] filterOrdinals,
			Object[] filterValues);

	public SelectResult<Object> selectMembers(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter,
			ClassSorterNode sorter, int pageSize, int pageOrdinal);

	public long selectCount(ClassStorage<?> aspect, ClassFilter filter);

	public long selectCountByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue);

	public long selectCountByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues);

}
