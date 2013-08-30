package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class DecoratedMember implements AspectMember {

	private final AspectMember _Member;

	public DecoratedMember(AspectMember member) {
		this._Member = member;
	}

	@Override
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this._Member.getAnnotation(annotationClass);
	}

	@Override
	public Annotation[] getAnnotations() {
		return this._Member.getAnnotations();
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {
		return this._Member.getDeclaredAnnotations();
	}

	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this._Member.isAnnotationPresent(annotationClass);
	}

	@Override
	public Class<?> getDeclaringClass() {
		return this._Member.getDeclaringClass();
	}

	@Override
	public int getModifiers() {
		return this._Member.getModifiers();
	}

	@Override
	public String getName() {
		return this._Member.getName();
	}

	@Override
	public boolean isSynthetic() {
		return this._Member.isSynthetic();
	}

	@Override
	public String getFullName() {
		return this._Member.getFullName();
	}

	@Override
	public Object getValue(Object instance) {
		return this._Member.getValue(instance);
	}

	@Override
	public void setValue(Object instance, Object value) {
		this._Member.setValue(instance, value);
	}

	@Override
	public boolean getBoolean(Object instance) {
		return this._Member.getBoolean(instance);
	}

	@Override
	public void setBoolean(Object instance, boolean value) {
		this._Member.setBoolean(instance, value);

	}

	@Override
	public byte getByte(Object instance) {
		return this._Member.getByte(instance);
	}

	@Override
	public void setByte(Object instance, byte value) {
		this._Member.setByte(instance, value);
	}

	@Override
	public char getChar(Object instance) {
		return this._Member.getChar(instance);
	}

	@Override
	public void setChar(Object instance, char value) {
		this._Member.setChar(instance, value);

	}

	@Override
	public double getDouble(Object instance) {
		return this._Member.getDouble(instance);
	}

	@Override
	public void setDouble(Object instance, double value) {
		this._Member.setDouble(instance, value);
	}

	@Override
	public float getFloat(Object instance) {
		return this._Member.getFloat(instance);
	}

	@Override
	public void setFloat(Object instance, float value) {
		this._Member.setFloat(instance, value);
	}

	@Override
	public int getInt(Object instance) {
		return this._Member.getInt(instance);

	}

	@Override
	public void setInt(Object instance, int value) {
		this._Member.setInt(instance, value);
	}

	@Override
	public short getShort(Object instance) {
		return this._Member.getShort(instance);

	}

	@Override
	public void setShort(Object instance, short value) {
		this._Member.setShort(instance, value);
	}

	@Override
	public long getLong(Object instance) {
		return this._Member.getLong(instance);
	}

	@Override
	public void setLong(Object instance, long value) {
		this._Member.setLong(instance, value);
	}

	@Override
	public Type getGenericType() {
		return this._Member.getGenericType();
	}

	@Override
	public Class<?> getType() {
		return this._Member.getType();
	}
}
