package com.eixox.data.text;

import java.lang.reflect.Field;
import java.util.List;

public public class TextAspect<G, T extends TextAspectMember> implements TextSchema<T> {

	protected abstract T decorate(Field field);
	
	
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	public T get(int ordinal) {
		// TODO Auto-generated method stub
		return null;
	}

	public T get(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getOrdinal(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getIdentityOrdinal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<T> getUniqueKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<T> getCompositeKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean ignoreBlankLines() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean ignoreCommentLines() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getCommentQualifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object[] parseRow(String line) {
		// TODO Auto-generated method stub
		return null;
	}

	public String formatRow(Object[] row) {
		// TODO Auto-generated method stub
		return null;
	}

	public int lineOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

}
