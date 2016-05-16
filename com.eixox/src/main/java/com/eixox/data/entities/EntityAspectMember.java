package com.eixox.data.entities;

import java.lang.reflect.Field;

import com.eixox.data.Aggregate;
import com.eixox.data.ColumnType;
import com.eixox.data.adapters.ValueAdapter;

public class EntityAspectMember<T> {

	public final EntityAspect<T> aspect;
	public final String columName;
	public final ColumnType columnType;
	public final boolean nullable;
	public final Field field;
	public final Aggregate aggregate;
	public final ValueAdapter<?> adapter;
	public final boolean updatable;

	private final ValueAdapter<?> getValueAdapter(Field field, Persistent persistent) {
		return null;
		/*
		 * Class<?> claz = persistent.adapterClass(); if
		 * (Object.class.equals(claz)) { ValueAdapter<?> va =
		 * ValueAdapterFactory.getAdapter(field.getType()); if (va == null)
		 * throw new RuntimeException(field.getType() +
		 * " has no Value Adapters."); else return va; } else { try { return
		 * (ValueAdapter<?>) claz.newInstance(); } catch (Exception ex) { throw
		 * new RuntimeException(ex); } }
		 */
	}

	public EntityAspectMember(EntityAspect<T> aspect, Field field, Persistent persistent) {
		this.field = field;
		this.aspect = aspect;
		this.columName = persistent.name().isEmpty() ? field.getName() : persistent.name();
		this.columnType = persistent.value();
		this.nullable = persistent.nullable();
		this.aggregate = persistent.aggregate();
		this.adapter = getValueAdapter(field, persistent);
		this.updatable = persistent.updatable();
	}

	public final ColumnType getColumnType() {
		return this.columnType;
	}

	public final String getColumnName() {
		return this.columName;
	}

	public final boolean isNullable() {
		return this.nullable;
	}

	public final Aggregate getAggregate() {
		return this.aggregate;
	}

	public final Object getValue(Object entity) {
		try {
			return this.field.get(entity);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public final void setValue(T entity, Object val) {
		try {
			this.field.set(entity, val);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public final String getName() {
		return this.field.getName();
	}

}
