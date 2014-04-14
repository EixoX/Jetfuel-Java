package com.eixox.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

public interface AspectMember extends AnnotatedElement, Member {

	public Class<?> getDataType();

	public Object getValue(Object instance);

	public void setValue(Object instance, Object value);
}
