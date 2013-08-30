package com.eixox.data;

import java.util.ArrayList;

import com.eixox.ArrayHelper;
import com.eixox.Pair;
import com.eixox.PairList;
import com.eixox.ValidationHelper;
import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

//_____________________________________________________________________________________________________________
public final class ClassStorage<T> extends AbstractAspect<ClassStorageMember> {

	private final String dataName;
	private final int identityOrdinal;
	private final int[] uniqueOrdinals;
	private final int[] primaryKeyOrdinals;
	private final ClassStorageEngine engine;

	// _____________________________________________________________________________________________________________
	public ClassStorage(Class<T> claz, ClassStorageEngine engine) {
		super(claz);

		Table table = claz.getAnnotation(Table.class);
		if (table == null)
			throw new RuntimeException("Please annotate " + claz.getName() + " as Table");

		if (super.getCount() == 0)
			throw new RuntimeException("Please annotate at lease one field of " + claz.getName() + " as a Column");

		this.engine = engine;
		this.dataName = table.dataName().isEmpty() ? claz.getSimpleName() : table.dataName();

		int count = super.getCount();
		int identity = -1;

		ArrayList<Integer> uos = new ArrayList<Integer>(4);
		ArrayList<Integer> pks = new ArrayList<Integer>(4);

		for (int i = 0; i < count; i++) {

			ClassStorageMember member = super.get(i);
			switch (member.getColumnType()) {
			case Identity:
				if (identity < 0) {
					identity = i;
				} else
					throw new RuntimeException(
							"Only one identity is permitted. Please remove the Identity Column type from "
									+ get(i).getFullName());
				break;
			case Unique:
				uos.add(i);
				break;
			case PrimaryKey:
				pks.add(i);
				break;
			default:
				break;
			}
		}

		this.identityOrdinal = identity;
		this.uniqueOrdinals = ArrayHelper.toIntArray(uos);
		this.primaryKeyOrdinals = ArrayHelper.toIntArray(pks);
	}

	// _____________________________________________________________________________________________________________
	@Override
	protected final ClassStorageMember decorate(AspectMember member) {
		Column column = member.getAnnotation(Column.class);
		return column == null ? null : new ClassStorageMember(member, column);
	}

	// _____________________________________________________________________________________________________________
	public final String getDataName() {
		return dataName;
	}

	// _____________________________________________________________________________________________________________
	public final String getDataName(int ordinal) {
		return super.get(ordinal).getDataName();
	}

	// _____________________________________________________________________________________________________________
	public final int getIdentityOrdinal() {
		return identityOrdinal;
	}

	// _____________________________________________________________________________________________________________
	public final String getIdentityDataName() {
		if (this.identityOrdinal >= 0)
			return super.get(this.identityOrdinal).getDataName();
		else
			return null;
	}

	// _____________________________________________________________________________________________________________
	public final ClassStorageMember getIdentityMember() {
		if (this.identityOrdinal >= 0)
			return super.get(this.identityOrdinal);
		else
			return null;
	}

	// _____________________________________________________________________________________________________________
	public final int getUniqueMemberCount() {
		return this.uniqueOrdinals == null ? 0 : this.uniqueOrdinals.length;
	}

	// _____________________________________________________________________________________________________________
	public final int getUniqueMemberOrdinal(int index) {
		return this.uniqueOrdinals[index];
	}

	// _____________________________________________________________________________________________________________
	public final String getUniqueMemberDataName(int index) {
		return super.get(this.uniqueOrdinals[index]).getDataName();
	}

	// _____________________________________________________________________________________________________________
	public final int getPrimaryKeyMemberCount() {
		return this.primaryKeyOrdinals == null ? 0 : this.primaryKeyOrdinals.length;
	}

	// _____________________________________________________________________________________________________________
	public final int getPrimaryKeyMemberOrdinal(int index) {
		return this.primaryKeyOrdinals[index];
	}

	// _____________________________________________________________________________________________________________
	public final String getPrimaryKeyDataName(int index) {
		return super.get(this.primaryKeyOrdinals[index]).getDataName();
	}

	// _____________________________________________________________________________________________________________
	public final Object[] getPrimaryKeyValues(Object entity) {
		Object[] arr = new Object[this.primaryKeyOrdinals.length];
		for (int i = 0; i < arr.length; i++)
			arr[i] = super.getValue(entity, primaryKeyOrdinals[i]);
		return arr;
	}

	// _____________________________________________________________________________________________________________
	public final long delete(T entity) {
		if (this.identityOrdinal >= 0) {
			Object identityValue = super.getValue(entity, this.identityOrdinal);
			if (!ValidationHelper.isNullOrEmpty(identityValue))
				return this.engine.deleteByMember(this, this.identityOrdinal, identityValue);
		}

		if (this.uniqueOrdinals != null)
			for (int i = 0; i < this.uniqueOrdinals.length; i++) {
				Object uniqueValue = super.getValue(entity, this.uniqueOrdinals[i]);
				if (!ValidationHelper.isNullOrEmpty(uniqueValue))
					return this.engine.deleteByMember(this, this.uniqueOrdinals[i], uniqueValue);
			}

		if (this.primaryKeyOrdinals != null && this.primaryKeyOrdinals.length > 0) {
			return this.engine.deleteByMembers(this, this.primaryKeyOrdinals, this.getPrimaryKeyValues(entity));
		}

		throw new RuntimeException("Unable to find members to delete " + getFullName());
	}

	// _____________________________________________________________________________________________________________
	public final Object ensureIdentity(T entity) {
		Object identityValue = super.getValue(entity, this.identityOrdinal);
		if (!ValidationHelper.isNullOrEmpty(identityValue))
			return identityValue;

		if (this.uniqueOrdinals != null)
			for (int i = 0; i < this.uniqueOrdinals.length; i++) {
				Object uniqueValue = super.getValue(entity, this.uniqueOrdinals[i]);
				if (!ValidationHelper.isNullOrEmpty(uniqueValue)) {
					identityValue = this.engine.selectMemberByMember(this, this.identityOrdinal,
							this.uniqueOrdinals[i], uniqueValue);
					if (!ValidationHelper.isNullOrEmpty(identityValue)) {
						super.setValue(entity, identityOrdinal, identityValue);
						return identityValue;
					}
				}
			}

		if (this.primaryKeyOrdinals != null && this.primaryKeyOrdinals.length > 0) {
			identityValue = this.engine.selectMemberByMembers(this, this.identityOrdinal, this.primaryKeyOrdinals,
					this.getPrimaryKeyValues(entity));
			if (!ValidationHelper.isNullOrEmpty(identityValue)) {
				super.setValue(entity, identityOrdinal, identityValue);
				return identityValue;
			}
		}

		return null;
	}

	// _____________________________________________________________________________________________________________
	private final PairList<Integer, Object> getValuesExcludingMember(Object entity, int memberOrdinal) {
		int count = super.getCount();
		PairList<Integer, Object> list = new PairList<Integer, Object>(count - 1);
		for (int i = 0; i < count; i++)
			if (i != memberOrdinal) {
				ClassStorageMember member = super.get(i);
				Object value = member.getValue(entity);
				if (ValidationHelper.isNullOrEmpty(value) && !member.isNullable())
					throw new RuntimeException(member.getFullName() + " does not allow nulls");
				list.add(new Pair<Integer, Object>(i, value));
			}

		return list;
	}

	// _____________________________________________________________________________________________________________
	private final PairList<Integer, Object> getValuesExcludingPrimaryKeys(Object entity) {
		int count = super.getCount();
		PairList<Integer, Object> list = new PairList<Integer, Object>(count - this.primaryKeyOrdinals.length);
		for (int i = 0; i < count; i++) {
			ClassStorageMember member = super.get(i);
			Object value = member.getValue(entity);
			if (member.getColumnType() != ColumnType.PrimaryKey) {
				if (ValidationHelper.isNullOrEmpty(value) && !member.isNullable())
					throw new RuntimeException(member.getFullName() + " does not allow nulls");
				list.add(new Pair<Integer, Object>(i, value));
			}
		}

		return list;
	}

	// _____________________________________________________________________________________________________________
	public final long update(T entity) {
		if (this.identityOrdinal >= 0) {
			Object identityValue = this.ensureIdentity(entity);
			if (identityValue == null)
				throw new RuntimeException("Unable to find identity value to update " + getFullName());
			else
				return this.engine.updateByMember(this, getValuesExcludingMember(entity, this.identityOrdinal),
						this.identityOrdinal, identityValue);
		}

		if (this.uniqueOrdinals != null)
			for (int i = 0; i < this.uniqueOrdinals.length; i++) {
				Object uniqueValue = super.getValue(entity, this.uniqueOrdinals[i]);
				if (!ValidationHelper.isNullOrEmpty(uniqueValue))
					return this.engine.updateByMember(this, getValuesExcludingMember(entity, this.uniqueOrdinals[i]),
							this.uniqueOrdinals[i], uniqueValue);
			}

		if (this.primaryKeyOrdinals != null && this.primaryKeyOrdinals.length > 0) {
			return this.engine.updateByMembers(this, this.getValuesExcludingPrimaryKeys(entity),
					this.primaryKeyOrdinals, this.getPrimaryKeyValues(entity));
		}

		throw new RuntimeException("Unable to find members to delete " + getFullName());

	}

	// _____________________________________________________________________________________________________________
	public final long insert(T entity) {
		if (this.identityOrdinal >= 0) {
			Object identityValue = this.engine.insertAndScopeIdentity(this,
					this.getValuesExcludingMember(entity, this.identityOrdinal), this.identityOrdinal);

			super.setValue(entity, identityOrdinal, identityValue);
			return ValidationHelper.isNullOrEmpty(identityValue) ? 0 : 1;
		} else {
			return this.engine.insert(this, getValuesExcludingMember(entity, -1));
		}
	}

	// _____________________________________________________________________________________________________________
	public final long save(T entity) {
		if (this.identityOrdinal >= 0) {
			Object identityValue = this.ensureIdentity(entity);
			if (identityValue == null)
				return this.insert(entity);
			else
				return this.engine.updateByMember(this, getValuesExcludingMember(entity, this.identityOrdinal),
						this.identityOrdinal, identityValue);
		}

		if (this.uniqueOrdinals != null)
			for (int i = 0; i < this.uniqueOrdinals.length; i++) {
				Object uniqueValue = super.getValue(entity, this.uniqueOrdinals[i]);
				if (!ValidationHelper.isNullOrEmpty(uniqueValue))
					return this.engine.updateByMember(this, getValuesExcludingMember(entity, this.uniqueOrdinals[i]),
							this.uniqueOrdinals[i], uniqueValue);
			}

		if (this.primaryKeyOrdinals != null && this.primaryKeyOrdinals.length > 0) {
			return this.engine.updateByMembers(this, this.getValuesExcludingPrimaryKeys(entity),
					this.primaryKeyOrdinals, this.getPrimaryKeyValues(entity));
		}

		return this.insert(entity);
	}

	// _____________________________________________________________________________________________________________
	public final Object selectMember(String memberName, String filterName, Object filterValue) {
		int ordinal = super.getOrdinalOrException(memberName);
		int filterOrdnal = super.getOrdinalOrException(filterName);
		return this.engine.selectMemberByMember(this, ordinal, filterOrdnal, filterValue);
	}

	// _____________________________________________________________________________________________________________
	public final long selectCount() {
		return this.engine.selectCount(this, null);
	}

	// _____________________________________________________________________________________________________________
	public final long selectCount(String memberName, Object memberValue) {
		return this.engine.selectCountByMember(this, super.getOrdinalOrException(memberName), memberValue);
	}

	// _____________________________________________________________________________________________________________
	public final T readByIdentity(Object identityValue) {
		if (ValidationHelper.isNullOrEmpty(identityValue)) {
			return null;
		} else {
			return this.engine.selectOneByMember(this, this.identityOrdinal, identityValue);
		}
	}

	// _____________________________________________________________________________________________________________
	public final T readByMember(String memberName, Object memberValue) {
		return this.engine.selectOneByMember(this, super.getOrdinalOrException(memberName), memberValue);
	}

	// _____________________________________________________________________________________________________________
	public final T readByPrimaryKey(Object... primaryKeyValues) {
		return this.engine.selectOneByMembers(this, this.primaryKeyOrdinals, primaryKeyValues);
	}

	// _____________________________________________________________________________________________________________
	public final T readByMembers(String[] memberNames, Object[] memberValues) {
		int[] ordinals = new int[memberNames.length];
		for (int i = 0; i < ordinals.length; i++)
			ordinals[i] = super.getOrdinalOrException(memberNames[i]);
		return this.engine.selectOneByMembers(this, ordinals, memberValues);
	}

	// _____________________________________________________________________________________________________________
	public final SelectResult<T> select(int pageSize, int pageOrdinal) {
		return this.engine.select(this, null, null, pageSize, pageOrdinal);
	}

	// _____________________________________________________________________________________________________________
	public final SelectResult<T> selectByMember(String filterName, Object filterValue, int pageSize, int pageOrdinal) {
		return this.engine.selectByMember(this, super.getOrdinalOrException(filterName), filterValue, pageSize,
				pageOrdinal);
	}

	// _____________________________________________________________________________________________________________
	public final SelectResult<T> selectByMembers(String[] filterNames, Object[] filterValues, int pageSize,
			int pageOrdinal) {

		return this.engine.selectByMembers(this, super.getOrdinals(filterNames), filterValues, pageSize, pageOrdinal);
	}

	// _____________________________________________________________________________________________________________
	public final ClassSelect<T> select() {
		return new ClassSelect<T>(this, this.engine);
	}

	// _____________________________________________________________________________________________________________
	public final ClassSelectMember selectMember(int ordinal) {
		return new ClassSelectMember(this, ordinal, engine);
	}

	// _____________________________________________________________________________________________________________
	public final ClassSelectMember selectMember(String memberName) {
		return new ClassSelectMember(this, memberName, engine);
	}

	// _____________________________________________________________________________________________________________
	public final ClassDelete createDelete() {
		return new ClassDelete(this, this.engine);
	}

	// _____________________________________________________________________________________________________________
	public final ClassUpdate createUpdate() {
		return new ClassUpdate(this, this.engine);
	}

	// _____________________________________________________________________________________________________________
	public final ClassInsert createInsert() {
		return new ClassInsert(this, this.engine);
	}

	

}
