package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public final class AspectField implements AspectMember {

	private final Field field;

	public AspectField(Field field) {
		this.field = field;
		this.field.setAccessible(true);
	}

	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.field.getAnnotation(annotationClass);
	}

	public final Annotation[] getAnnotations() {
		return this.field.getAnnotations();
	}

	public final Annotation[] getDeclaredAnnotations() {
		return this.field.getDeclaredAnnotations();
	}

	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this.field.isAnnotationPresent(annotationClass);
	}

	public final Class<?> getDeclaringClass() {
		return this.field.getDeclaringClass();
	}

	public final int getModifiers() {
		return this.field.getModifiers();
	}

	public final String getName() {
		return this.field.getName();
	}

	public final boolean isSynthetic() {
		return this.field.isSynthetic();
	}

	public final Class<?> getDataType() {
		return this.field.getType();
	}

	public final Object getValue(Object instance) {
		try {
			return this.field.get(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void setValue(Object instance, Object value) {
		try {
			this.field.set(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
