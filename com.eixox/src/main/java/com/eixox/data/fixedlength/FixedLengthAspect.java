package com.eixox.data.fixedlength;

import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class FixedLengthAspect<T> extends te

	private FixedLengthAspect(Class<?> dataType) {
		super(dataType);
	}

	@Override
	protected FixedLengthAspectMember decorate(AspectMember member) {
		FixedLength fl = member.getAnnotation(FixedLength.class);
		return fl == null ? null : new FixedLengthAspectMember(member, fl);
	}

	public T parse(String line) {
		@SuppressWarnings("unchecked")
		T item = (T) newInstance();
		for (FixedLengthAspectMember child : this) {
			Object v = child.parse(line);
			child.setValue(item, v);
		}
		return item;
	}

	private static final HashMap<Class<?>, FixedLengthAspect<?>> INSTANCES = new HashMap<Class<?>, FixedLengthAspect<?>>();

	@SuppressWarnings("unchecked")
	public static synchronized final <T> FixedLengthAspect<T> getInstance(Class<T> claz) {
		FixedLengthAspect<?> aspect = INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new FixedLengthAspect<T>(claz);
			INSTANCES.put(claz, aspect);
		}
		return (FixedLengthAspect<T>) aspect;
	}
}
