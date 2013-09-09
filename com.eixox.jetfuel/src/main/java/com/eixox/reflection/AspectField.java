package com.eixox.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class AspectField implements AspectMember {

	private final Field _Field;

	public AspectField(Field field) {
		this._Field = field;
		this._Field.setAccessible(true);
	}

	
	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return _Field.getAnnotation(annotationClass);
	}

	
	public final Annotation[] getAnnotations() {
		return _Field.getAnnotations();
	}

	
	public final Annotation[] getDeclaredAnnotations() {
		return this._Field.getDeclaredAnnotations();
	}

	
	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this._Field.isAnnotationPresent(annotationClass);
	}

	
	public final Class<?> getDeclaringClass() {
		return this._Field.getDeclaringClass();
	}

	
	public final int getModifiers() {
		return this._Field.getModifiers();
	}

	
	public final String getName() {
		return this._Field.getName();
	}

	
	public final boolean isSynthetic() {
		return this._Field.isSynthetic();
	}

	
	public final String getFullName() {
		return this._Field.getDeclaringClass().getName() + "." + this._Field.getName();
	}

	
	public final Object getValue(Object instance) {
		try {
			return this._Field.get(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setValue(Object instance, Object value) {
		try {
			this._Field.set(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final boolean getBoolean(Object instance) {
		try {
			return this._Field.getBoolean(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setBoolean(Object instance, boolean value) {
		try {
			this._Field.setBoolean(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final byte getByte(Object instance) {
		try {
			return this._Field.getByte(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setByte(Object instance, byte value) {
		try {
			this._Field.setByte(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final char getChar(Object instance) {
		try {
			return this._Field.getChar(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setChar(Object instance, char value) {
		try {
			this._Field.setChar(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final double getDouble(Object instance) {
		try {
			return this._Field.getDouble(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setDouble(Object instance, double value) {
		try {
			this._Field.setDouble(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	
	public final float getFloat(Object instance) {
		try {
			return this._Field.getFloat(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setFloat(Object instance, float value) {
		try {
			this._Field.setFloat(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final int getInt(Object instance) {
		try {
			return this._Field.getInt(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final void setInt(Object instance, int value) {
		try {
			this._Field.setInt(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
	public final short getShort(Object instance) {
		try {
			return this._Field.getShort(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void setShort(Object instance, short value) {
		try {
			this._Field.setShort(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final long getLong(Object instance) {
		try {
			return this._Field.getLong(instance);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void setLong(Object instance, long value) {
		try {
			this._Field.setLong(instance, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Type getGenericType() {
		return this._Field.getGenericType();
	}

	public final Class<?> getType() {
		return this._Field.getType();
	}
}
