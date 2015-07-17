package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public final class AspectProperty implements AspectMember {

	private final Method getter;
	private final Method setter;
	private final String name;

	public AspectProperty(String name, Method getter, Method setter) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
	}

	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.getter.getAnnotation(annotationClass);
	}

	public final Annotation[] getAnnotations() {
		return this.getter.getAnnotations();
	}

	public final Annotation[] getDeclaredAnnotations() {
		return this.getter.getDeclaredAnnotations();
	}

	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this.getter.isAnnotationPresent(annotationClass);
	}

	public final Class<?> getDeclaringClass() {
		return this.getter.getDeclaringClass();
	}

	public final int getModifiers() {
		return this.getter.getModifiers();
	}

	public final String getName() {
		return this.name;
	}

	public final boolean isSynthetic() {
		return this.getter.isSynthetic();
	}

	public final Class<?> getDataType() {
		return this.getter.getReturnType();
	}

	public final Object getValue(Object instance) {
		try {
			return this.getter.invoke(instance, (Object[]) null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void setValue(Object instance, Object value) {
		try {
			this.setter.invoke(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Type getGenericType() {
		return this.getter.getGenericReturnType();
	}

	public final boolean isReadOnly() {
		return this.setter != null;
	}

}
