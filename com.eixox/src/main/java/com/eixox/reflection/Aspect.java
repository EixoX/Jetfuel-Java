package com.eixox.reflection;

import java.lang.reflect.AnnotatedElement;

public interface Aspect extends AnnotatedElement {

	public Class<?> getDataType();
	
	public String getName();
}
