package com.eixox.database;

import java.util.HashMap;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;
import com.eixox.reflection.AspectMember;

public final class DatabaseAspect extends EntityAspect {

	private static String getTableName(Class<?> dataType) {
		DatabaseTable annotation = dataType.getAnnotation(DatabaseTable.class);
		if (annotation == null)
			throw new RuntimeException("Please annotate " + dataType + " as a DatabaseTable if you wish to query it");
		else
			return annotation.tableName();

	}

	private DatabaseAspect(Class<?> dataType) {
		super(dataType, getTableName(dataType));
	}

	@Override
	protected final EntityAspectMember decorate(AspectMember member) {
		DatabaseColumn col = member.getAnnotation(DatabaseColumn.class);
		if (col == null)
			return null;
		else
			return new EntityAspectMember(member, col.type(), col.columnName(), col.nullable(), col.readonly());
	}

	private static final HashMap<Class<?>, DatabaseAspect> instances = new HashMap<Class<?>, DatabaseAspect>();

	public static DatabaseAspect getInstance(Class<?> claz) {
		DatabaseAspect aspect = instances.get(claz);
		if (aspect == null) {
			aspect = new DatabaseAspect(claz);
			instances.put(claz, aspect);
		}
		return aspect;
	}
}
