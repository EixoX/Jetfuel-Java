package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class DecoratedMember implements AspectMember {

	private final AspectMember _Member;

	public DecoratedMember(AspectMember member) {
		this._Member = member;
	}

	
	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this._Member.getAnnotation(annotationClass);
	}

	
	public Annotation[] getAnnotations() {
		return this._Member.getAnnotations();
	}

	
	public Annotation[] getDeclaredAnnotations() {
		return this._Member.getDeclaredAnnotations();
	}

	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this._Member.isAnnotationPresent(annotationClass);
	}

	
	public Class<?> getDeclaringClass() {
		return this._Member.getDeclaringClass();
	}

	
	public int getModifiers() {
		return this._Member.getModifiers();
	}

	
	public String getName() {
		return this._Member.getName();
	}

	
	public boolean isSynthetic() {
		return this._Member.isSynthetic();
	}

	
	public String getFullName() {
		return this._Member.getFullName();
	}

	
	public Object getValue(Object instance) {
		return this._Member.getValue(instance);
	}

	
	public void setValue(Object instance, Object value) {
		this._Member.setValue(instance, value);
	}

	
	public boolean getBoolean(Object instance) {
		return this._Member.getBoolean(instance);
	}

	
	public void setBoolean(Object instance, boolean value) {
		this._Member.setBoolean(instance, value);

	}

	
	public byte getByte(Object instance) {
		return this._Member.getByte(instance);
	}

	
	public void setByte(Object instance, byte value) {
		this._Member.setByte(instance, value);
	}

	
	public char getChar(Object instance) {
		return this._Member.getChar(instance);
	}

	
	public void setChar(Object instance, char value) {
		this._Member.setChar(instance, value);

	}

	
	public double getDouble(Object instance) {
		return this._Member.getDouble(instance);
	}

	
	public void setDouble(Object instance, double value) {
		this._Member.setDouble(instance, value);
	}

	
	public float getFloat(Object instance) {
		return this._Member.getFloat(instance);
	}

	
	public void setFloat(Object instance, float value) {
		this._Member.setFloat(instance, value);
	}

	
	public int getInt(Object instance) {
		return this._Member.getInt(instance);

	}

	
	public void setInt(Object instance, int value) {
		this._Member.setInt(instance, value);
	}

	
	public short getShort(Object instance) {
		return this._Member.getShort(instance);

	}

	
	public void setShort(Object instance, short value) {
		this._Member.setShort(instance, value);
	}

	
	public long getLong(Object instance) {
		return this._Member.getLong(instance);
	}

	
	public void setLong(Object instance, long value) {
		this._Member.setLong(instance, value);
	}

	
	public Type getGenericType() {
		return this._Member.getGenericType();
	}

	
	public Class<?> getType() {
		return this._Member.getType();
	}
}
