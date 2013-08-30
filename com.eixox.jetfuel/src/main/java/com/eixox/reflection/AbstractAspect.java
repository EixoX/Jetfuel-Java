package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class AbstractAspect<TMember extends DecoratedMember> implements Aspect, Iterable<TMember> {

	private final Class<?> _Type;
	private final ArrayList<TMember> _Members;

	protected abstract TMember decorate(AspectMember member);

	public AbstractAspect(Class<?> claz) {

		this._Type = claz;
		Field[] fields = claz.getDeclaredFields();

		this._Members = new ArrayList<TMember>(fields.length);
		for (int i = 0; i < fields.length; i++) {
			AspectMember aspectMember = new AspectField(fields[i]);
			TMember member = decorate(aspectMember);
			if (member != null)
				this._Members.add(member);
		}

	}

	public final Class<?> getType() {
		return this._Type;
	}

	@Override
	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return _Type.getAnnotation(annotationClass);
	}

	@Override
	public final Annotation[] getAnnotations() {
		return _Type.getAnnotations();
	}

	@Override
	public final Annotation[] getDeclaredAnnotations() {
		return _Type.getDeclaredAnnotations();
	}

	@Override
	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return _Type.isAnnotationPresent(annotationClass);
	}

	@Override
	public final String getName() {
		return _Type.getSimpleName();
	}

	@Override
	public final String getFullName() {
		return _Type.getName();
	}

	@Override
	public final Object getValue(Object instance, int ordinal) {
		return _Members.get(ordinal).getValue(instance);
	}

	@Override
	public final Object getValue(Object instance, String name) {
		return getValue(instance, getOrdinalOrException(name));
	}

	@Override
	public final void setValue(Object instance, int ordinal, Object value) {
		_Members.get(ordinal).setValue(instance, value);
	}

	@Override
	public final void setValue(Object instance, String name, Object value) {
		setValue(instance, getOrdinalOrException(name), value);

	}

	@Override
	public final int getOrdinal(String name) {
		int count = _Members.size();
		for (int i = 0; i < count; i++)
			if (name.equalsIgnoreCase(_Members.get(i).getName()))
				return i;

		return -1;
	}

	@Override
	public final int getOrdinalOrException(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException(name + " not found on " + getFullName());
		else
			return ordinal;
	}

	@Override
	public final int getCount() {
		return _Members.size();
	}

	@Override
	public final AspectMember getAspectMember(int ordinal) {
		return _Members.get(ordinal);
	}

	@Override
	public final AspectMember getAspectMember(String name) {
		return _Members.get(getOrdinalOrException(name));
	}

	public final TMember get(int ordinal) {
		return _Members.get(ordinal);
	}

	public final TMember get(String name) {
		return get(getOrdinalOrException(name));
	}

	@Override
	public Iterator<TMember> iterator() {
		return _Members.iterator();
	}

	public final int[] getOrdinals(String... names) {
		int[] ordinals = new int[names.length];
		for (int i = 0; i < ordinals.length; i++)
			ordinals[i] = getOrdinalOrException(names[i]);
		return ordinals;
	}

	public final Object newInstance() throws InstantiationException, IllegalAccessException {
		return this._Type.newInstance();
	}

	public final Class<?> getType(int ordinal) {
		return this._Members.get(ordinal).getType();
	}

}
