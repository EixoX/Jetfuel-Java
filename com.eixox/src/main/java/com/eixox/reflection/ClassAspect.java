package com.eixox.reflection;

import java.util.HashMap;

public class ClassAspect<T> extends AbstractAspect<ClassAspectMember> {

	private ClassAspect(Class<?> dataType) {
		super(dataType);
	}

	@Override
	protected ClassAspectMember decorate(AspectMember member) {
		return new ClassAspectMember(member);
	}

	private static final HashMap<Class<?>, ClassAspect<?>> instances = new HashMap<Class<?>, ClassAspect<?>>();

	@SuppressWarnings("unchecked")
	public static synchronized final <T> ClassAspect<T> getInstance(Class<T> claz) {
		ClassAspect<?> classAspect = instances.get(claz);
		if (classAspect == null) {
			classAspect = new ClassAspect<T>(claz);
			instances.put(claz, classAspect);
		}
		return (ClassAspect<T>) classAspect;
	}
}
