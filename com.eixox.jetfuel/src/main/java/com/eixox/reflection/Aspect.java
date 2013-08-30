package com.eixox.reflection;

import java.lang.reflect.AnnotatedElement;

public interface Aspect extends AnnotatedElement {

	public Class<?> getType();

	public Class<?> getType(int ordinal);

	public String getName();

	public String getFullName();

	public Object getValue(Object instance, int ordinal);

	public Object getValue(Object instance, String name);

	public void setValue(Object instance, int ordinal, Object value);

	public void setValue(Object instance, String name, Object value);

	public int getOrdinal(String name);

	public int getOrdinalOrException(String name);

	public int getCount();

	public AspectMember getAspectMember(int ordinal);

	public AspectMember getAspectMember(String name);

}
