package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractAspect<G extends AbstractAspectMember> implements Aspect, Iterable<G> {

	private final Class<?> dataType;
	private final ArrayList<G> members;

	protected AbstractAspect(Class<?> dataType) {
		this.dataType = dataType;
		final Method[] methods = dataType.getMethods();
		final Field[] fields = dataType.getDeclaredFields();
		this.members = new ArrayList<G>(methods.length + fields.length);
		for (int i = 0; i < fields.length; i++) {
			final AspectMember fieldMember = new AspectField(fields[i]);
			final G child = decorate(fieldMember);
			if (child != null)
				members.add(child);
		}
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getParameterTypes().length == 0 && methods[i].getName().startsWith("get")) {
				final String name = methods[i].getName().substring(3, methods[i].getName().length());
				Method setter = null;
				try {
					setter = dataType.getMethod("set" + name, methods[i].getReturnType());
				} catch (Exception e) {
				}
				final AspectMember property = new AspectProperty(name, methods[i], setter);
				final G child = decorate(property);
				if (child != null)
					members.add(child);
			}
		}

	}

	protected abstract G decorate(AspectMember member);

	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return this.dataType.getAnnotation(annotationClass);
	}

	public final Annotation[] getAnnotations() {
		return this.dataType.getAnnotations();
	}

	public final Annotation[] getDeclaredAnnotations() {
		return this.dataType.getDeclaredAnnotations();
	}

	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this.dataType.isAnnotationPresent(annotationClass);
	}

	public final Class<?> getDataType() {
		return this.dataType;
	}

	public final G get(int ordinal) {
		return this.members.get(ordinal);
	}

	public final String getName(int ordinal) {
		return this.members.get(ordinal).getName();
	}

	public final G get(String name) {
		final int ordinal = getOrdinal(name);
		return ordinal >= 0 ? this.members.get(ordinal) : null;
	}

	public final int getOrdinal(String name) {
		if (name != null && !name.isEmpty()) {
			final int l = this.members.size();
			for (int i = 0; i < l; i++)
				if (name.equalsIgnoreCase(this.members.get(i).getName()))
					return i;
		}
		return -1;
	}

	public final int getOrdinalOrException(String name) {
		final int i = getOrdinal(name);
		if (i < 0)
			throw new RuntimeException(name + " is not present on " + this.dataType);
		else
			return i;
	}

	public final String getName() {
		return this.dataType.getName();
	}

	public final int getCount() {
		return this.members.size();
	}

	public final Object getValue(Object entity, int ordinal) {
		return this.members.get(ordinal).getValue(entity);
	}

	public final Object getValue(Object entity, String name) {
		return this.members.get(getOrdinalOrException(name)).getValue(entity);
	}

	public final void setValue(Object entity, int ordinal, Object value) {
		this.members.get(ordinal).setValue(entity, value);
	}

	public final void getValue(Object entity, String name, Object value) {
		this.members.get(getOrdinalOrException(name)).setValue(entity, value);
	}

	public final Iterator<G> iterator() {
		return this.members.iterator();
	}

	public final Object newInstance() {
		try {
			return this.dataType.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public final G[] getMembers(String... names) {
		@SuppressWarnings("unchecked")
		G[] mems = (G[]) Array.newInstance(this.members.get(0).getClass(), names.length);
		for (int i = 0; i < names.length; i++)
			mems[i] = get(getOrdinalOrException(names[i]));
		return mems;
	}
}
