package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

public class AbstractAspectMember implements AspectMember {

	private final AspectMember member;

	public AbstractAspectMember(AspectMember member) {
		this.member = member;
	}

	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.member.getAnnotation(annotationClass);
	}

	public final Annotation[] getAnnotations() {
		return this.member.getAnnotations();
	}

	public final Annotation[] getDeclaredAnnotations() {
		return this.member.getDeclaredAnnotations();
	}

	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this.member.isAnnotationPresent(annotationClass);
	}

	public final Class<?> getDeclaringClass() {
		return this.member.getDeclaringClass();
	}

	public final int getModifiers() {
		return this.member.getModifiers();
	}

	public final String getName() {
		return this.member.getName();
	}

	public final boolean isSynthetic() {
		return this.member.isSynthetic();
	}

	public final Class<?> getDataType() {
		return this.member.getDataType();
	}

	public final Object getValue(Object instance) {
		return this.member.getValue(instance);
	}

	public final void setValue(Object instance, Object value) {
		this.member.setValue(instance, value);
	}

	public final Type getGenericType() {
		return this.member.getGenericType();
	}

	public final String getValueToString(Object instance) {
		Object value = this.member.getValue(instance);
		if (value == null)
			return "";
		else
			return value.toString();
	}

	public final boolean isReadOnly() {
		return this.member.isReadOnly();
	}

	public final boolean isArray() {
		return getDataType().isArray();
	}

	public final boolean isList() {
		return List.class.isAssignableFrom(getDataType());
	}

	public final boolean isPrimitive() {
		return getDataType().isPrimitive();
	}

	public final boolean isEnum() {
		return getDataType().isEnum();
	}

	public final boolean isString() {
		return String.class.isAssignableFrom(getDataType());
	}
	
	
}
