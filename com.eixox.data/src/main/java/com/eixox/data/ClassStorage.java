package com.eixox.data;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class ClassStorage {

	private final Class<?> claz;
	private final ArrayList<ClassStorageColumn> columns;
	private String dataName;
	private int identityOrdinal = -1;

	public ClassStorage(Class<?> claz) {

		this.claz = claz;
		this.dataName = claz.getName();

		Field[] declaredFields = this.claz.getDeclaredFields();
		this.columns = new ArrayList<ClassStorageColumn>(declaredFields.length);
		for (int i = 0; i < declaredFields.length; i++) {
			declaredFields[i].setAccessible(true);
			this.columns.add(new ClassStorageColumn(declaredFields[i]));
		}
	}

	/**
	 * @return the dataName
	 */
	public final String getDataName() {
		return dataName;
	}

	/**
	 * @param dataName
	 *            the dataName to set
	 */
	public final void setDataName(String dataName) {
		this.dataName = dataName;
	}

	/**
	 * @return the claz
	 */
	public final Class<?> getClaz() {
		return claz;
	}

	/**
	 * @return the columns
	 */
	public final ArrayList<ClassStorageColumn> getColumns() {
		return columns;
	}

	public final ClassStorageColumn getColumn(int ordinal) {
		return this.columns.get(ordinal);
	}

	public final ClassStorageColumn getColumn(String name) {
		return this.columns.get(indexOf(name));
	}

	public final boolean removeColumn(int ordinal) {
		if (ordinal >= 0 && ordinal < this.columns.size()) {
			this.columns.remove(ordinal);
			return true;
		} else
			return false;
	}

	public final boolean removeColumn(String name) {
		return removeColumn(indexOf(name));
	}

	public final int indexOf(String name) {
		if (name != null && !name.isEmpty()) {
			int l = this.columns.size();
			for (int i = 0; i < l; i++)
				if (name.equalsIgnoreCase(this.columns.get(i).getDataName()))
					return i;
		}
		return -1;
	}

	public final int indexOfField(String fieldName) {
		if (fieldName != null && !fieldName.isEmpty()) {
			int l = this.columns.size();
			for (int i = 0; i < l; i++)
				if (fieldName.equalsIgnoreCase(this.columns.get(i).getFieldName()))
					return i;
		}
		return -1;
	}

	public final int getIndentityOrdinal() {
		return this.identityOrdinal;
	}

	public final void setIdentityOrdinal(int ordinal) {
		this.identityOrdinal = ordinal;
	}

	public final String getIdentityName() {
		return this.identityOrdinal >= 0 ? this.columns.get(identityOrdinal).getDataName() : null;
	}

	public final void setIdentityName(String name) {
		this.identityOrdinal = indexOf(name);
	}

	public final String getDataName(int ordinal) {
		return ordinal < 0 ? null : this.columns.get(ordinal).getDataName();
	}

	public final void setDataName(int ordinal, String dataName) {
		this.columns.get(ordinal).setDataName(dataName);
	}

	public final String getDataName(String fieldName) {
		return getDataName(indexOfField(fieldName));
	}

	public final boolean setDataName(String fieldName, String dataName) {
		int ordinal = indexOfField(fieldName);
		if (ordinal < 0)
			return false;
		this.columns.get(ordinal).setDataName(dataName);
		return true;
	}

}
