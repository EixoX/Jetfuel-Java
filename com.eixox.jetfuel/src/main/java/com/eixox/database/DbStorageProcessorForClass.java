package com.eixox.database;

import java.sql.ResultSet;

import com.eixox.data.ClassStorage;
import com.eixox.data.ClassStorageMember;
import com.eixox.data.SelectResult;

public class DbStorageProcessorForClass<T> implements ResultsetProcessor<SelectResult<T>> {

	private final ClassStorage<T> aspect;
	private final int pageSize;
	private final int pageOrdinal;

	public DbStorageProcessorForClass(ClassStorage<T> aspect, int pageSize, int pageOrdinal) {
		this.aspect = aspect;
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
	}

	@SuppressWarnings({ "unchecked" })
	public SelectResult<T> process(ResultSet resultSet) {

		SelectResult<T> result = new SelectResult<T>(pageSize, pageOrdinal);
		int count = aspect.getCount();
		try {

			while (resultSet.next()) {
				T instance = (T) this.aspect.newInstance();
				for (int i = 0; i < count; i++) {
					Object value = resultSet.getObject(i + 1);
					ClassStorageMember member = this.aspect.get(i);
					if (value != null) {
						value = member.getValueAdapter().adapt(value);
						member.setValue(instance, value);
					}
				}
				result.add(instance);
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}

}
