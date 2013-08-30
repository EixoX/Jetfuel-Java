package com.eixox;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public final class ReflectionHelper {

	public static final Type getGenericTypeOfInstance(Object instance) {
		return getGenericType(instance.getClass());
	}

	public static final Type getGenericType(Class<?> claz) {
		Type type = claz.getGenericSuperclass();
		if (type == null) {
			Type[] genericInterfaces = claz.getGenericInterfaces();
			for (Type genericInterface : genericInterfaces) {
				if (genericInterface instanceof ParameterizedType) {
					Type[] genericTypes = ((ParameterizedType) genericInterface).getActualTypeArguments();
					if (genericTypes.length > 0)
						return genericTypes[0];
				}
			}
		} else {
			if (type instanceof ParameterizedType) {
				Type[] genericTypes = ((ParameterizedType) type).getActualTypeArguments();
				return genericTypes[0];
			}
		}
		return null;
	}

	public static final boolean isNumeric(Class<?> claz) {
		return Number.class.isAssignableFrom(claz) || Byte.TYPE.isAssignableFrom(claz)
				|| Short.TYPE.isAssignableFrom(claz) || Integer.TYPE.isAssignableFrom(claz)
				|| Long.TYPE.isAssignableFrom(claz) || Float.TYPE.isAssignableFrom(claz)
				|| Double.TYPE.isAssignableFrom(claz);

	}

}
