package com.eixox.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public interface AspectMember extends AnnotatedElement, Member {

	public Class<?> getDataType();

	public Object getValue(Object instance);

	public void setValue(Object instance, Object value);

	public Type getGenericType();
	
	public boolean isReadOnly();
	
}
