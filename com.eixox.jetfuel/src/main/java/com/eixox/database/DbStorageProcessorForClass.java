package com.eixox.database;

import java.sql.ResultSet;

import com.eixox.data.ClassStorage;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SelectResult<T> process(ResultSet resultSet) {

		SelectResult<T> result = new SelectResult<T>(pageSize, pageOrdinal);
		int count = aspect.getCount();
		try {

			while (resultSet.next()) {
				T instance = (T) this.aspect.newInstance();
				for (int i = 0; i < count; i++) {
					Object value = resultSet.getObject(i + 1);
					Class<?> dataType = this.aspect.getType(i);
					if (value != null) {
						if (dataType.isEnum())
							value = Enum.valueOf((Class<Enum>) dataType, value.toString());
						this.aspect.setValue(instance, i, value);
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
