package com.eixox.data.database;

import java.lang.reflect.Field;

import com.eixox.data.ClassDelete;
import com.eixox.data.ClassInsert;
import com.eixox.data.ClassSelect;
import com.eixox.data.ClassStorage;
import com.eixox.data.ClassStorageColumn;
import com.eixox.data.ClassUpdate;
import com.eixox.data.Table;

public class SqlClassStorage extends ClassStorage {

	private final SqlDatabase database;

	public static final String findTableName(Class<?> dataType) {
		Table annotation = dataType.getAnnotation(Table.class);
		if (annotation == null || annotation.name() == null || annotation.name().isEmpty())
			return dataType.getName();
		else
			return annotation.name();
	}

	public SqlClassStorage(Class<?> dataType, SqlDatabase database) {
		super(dataType, findTableName(dataType));
		this.database = database;
	}

	public final SqlDatabase getDatabase() {
		return this.database;
	}

	@Override
	protected ClassStorageColumn map(Field field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final ClassDelete delete() {
		return new SqlClassDelete(this);
	}

	@Override
	public final ClassUpdate update() {
		return new SqlClassUpdate(this);
	}

	@Override
	public final ClassInsert insert() {
		return new SqlClassInsert(this);
	}

	@Override
	public final <T> ClassSelect<T> select() {
		return new SqlClassSelect(this);
	}

}
