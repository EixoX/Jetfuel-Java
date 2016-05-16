package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface ValueAdapter<T> {

	public Class<T> getDataType();

	public String format(T value);

	public T parse(String input);

	public void parseIntoField(String source, Field field, Object target);

	public void readIntoField(ResultSet source, int position, Field field, Object target);

	public void readIntoField(ResultSet source, String name, Field field, Object target);

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position);

	public void formatSql(T source, StringBuilder target);
}
