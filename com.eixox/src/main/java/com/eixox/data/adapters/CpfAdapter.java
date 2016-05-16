package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CpfAdapter implements ValueAdapter<Long> {

	public Class<Long> getDataType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String format(Long value) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long parse(String input) {
		// TODO Auto-generated method stub
		return null;
	}

	public void parseIntoField(String source, Field field, Object target) {
		// TODO Auto-generated method stub
		
	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		// TODO Auto-generated method stub
		
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		// TODO Auto-generated method stub
		
	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		// TODO Auto-generated method stub
		
	}

	public void formatSql(Long source, StringBuilder target) {
		// TODO Auto-generated method stub
		
	}

}
