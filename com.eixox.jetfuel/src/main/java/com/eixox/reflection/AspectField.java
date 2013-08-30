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

	@Override
	public final <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return _Field.getAnnotation(annotationClass);
	}

	@Override
	public final Annotation[] getAnnotations() {
		return _Field.getAnnotations();
	}

	@Override
	public final Annotation[] getDeclaredAnnotations() {
		return this._Field.getDeclaredAnnotations();
	}

	@Override
	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return this._Field.isAnnotationPresent(annotationClass);
	}

	@Override
	public final Class<?> getDeclaringClass() {
		return this._Field.getDeclaringClass();
	}

	@Override
	public final int getModifiers() {
		return this._Field.getModifiers();
	}

	@Override
	public final String getName() {
		return this._Field.getName();
	}

	@Override
	public final boolean isSynthetic() {
		return this._Field.isSynthetic();
	}

	@Override
	public final String getFullName() {
		return this._Field.getDeclaringClass().getName() + "." + this._Field.getName();
	}

	@Override
	public final Object getValue(Object instance) {
		try {
			return this._Field.get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setValue(Object instance, Object value) {
		try {
			this._Field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final boolean getBoolean(Object instance) {
		try {
			return this._Field.getBoolean(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setBoolean(Object instance, boolean value) {
		try {
			this._Field.setBoolean(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final byte getByte(Object instance) {
		try {
			return this._Field.getByte(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setByte(Object instance, byte value) {
		try {
			this._Field.setByte(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final char getChar(Object instance) {
		try {
			return this._Field.getChar(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setChar(Object instance, char value) {
		try {
			this._Field.setChar(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final double getDouble(Object instance) {
		try {
			return this._Field.getDouble(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setDouble(Object instance, double value) {
		try {
			this._Field.setDouble(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public final float getFloat(Object instance) {
		try {
			return this._Field.getFloat(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setFloat(Object instance, float value) {
		try {
			this._Field.setFloat(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final int getInt(Object instance) {
		try {
			return this._Field.getInt(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setInt(Object instance, int value) {
		try {
			this._Field.setInt(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final short getShort(Object instance) {
		try {
			return this._Field.getShort(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setShort(Object instance, short value) {
		try {
			this._Field.setShort(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final long getLong(Object instance) {
		try {
			return this._Field.getLong(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final void setLong(Object instance, long value) {
		try {
			this._Field.setLong(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public final Type getGenericType() {
		return this._Field.getGenericType();
	}

	@Override
	public final Class<?> getType() {
		return this._Field.getType();
	}
}
