package com.eixox.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.lang.reflect.Type;

public interface AspectMember extends AnnotatedElement, Member {

	// Gets the class + member name;
	public String getFullName();

	// Gets the value of a static or instance field.
	public Object getValue(Object instance);

	// Sets the value of a static or instance field.
	public void setValue(Object instance, Object value);

	// Gets the value of a static or instance boolean field.
	public boolean getBoolean(Object instance);

	// Sets the value of a static or instance boolean field.
	public void setBoolean(Object instance, boolean value);

	// Gets the value of a static or instance byte field.
	public byte getByte(Object instance);

	// Sets the value of a static or instance byte field.
	public void setByte(Object instance, byte value);

	// Gets the value of a static or instance field of type char or of another
	// primitive type convertible to type char via a widening conversion.
	public char getChar(Object instance);

	// Sets the value of a static or instance field of type char or of another
	// primitive type convertible to type char via a widening conversion.
	public void setChar(Object instance, char value);

	// Gets the value of a static or instance field of type double or of another
	// primitive type convertible to type double via a widening conversion.
	public double getDouble(Object instance);

	// Sets the value of a static or instance field of type double or of another
	// primitive type convertible to type double via a widening conversion.
	public void setDouble(Object instance, double value);

	// Gets the value of a static or instance field of type float or of another
	// primitive type convertible to type float via a widening conversion.
	public float getFloat(Object instance);

	// Sets the value of a static or instance field of type float or of another
	// primitive type convertible to type float via a widening conversion.
	public void setFloat(Object instance, float value);

	// Gets the value of a static or instance field of type int or of another
	// primitive type convertible to type int via a widening conversion.
	public int getInt(Object instance);

	// Sets the value of a static or instance field of type int or of another
	// primitive type convertible to type int via a widening conversion.
	public void setInt(Object instance, int value);

	// Gets the value of a static or instance field of type short or of another
	// primitive type convertible to type short via a widening conversion.
	public short getShort(Object instance);

	// Sets the value of a static or instance field of type short or of another
	// primitive type convertible to type short via a widening conversion.
	public void setShort(Object instance, short value);

	// Gets the value of a static or instance field of type long or of another
	// primitive type convertible to type long via a widening conversion.
	public long getLong(Object instance);

	// Sets the value of a static or instance field of type long or of another
	// primitive type convertible to type long via a widening conversion.
	public void setLong(Object instance, long value);

	// Returns a Type instance that represents the declared type for the field
	// represented by this Field instance.
	public Type getGenericType();
	
	// Returns a Class instance that identifies the declared type for the field
	// represented by this Field instance.
	public Class<?> getType();

}
