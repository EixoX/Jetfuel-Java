package com.eixox.data;

import java.lang.reflect.Field;

public class ClassStorageColumn {

	private final Field field;
	private String dataName;
	private int position;
	private int offset;
	private int length;
	private boolean unique;
	private boolean primaryKey;
	private boolean identity;
	private boolean generated;
	private boolean trans;

	public ClassStorageColumn(Field field) {
		this.field = field;
		this.dataName = field.getName();
		
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
	 * @return the position
	 */
	public final int getPosition() {
		return position;
	}

	/**
	 * @param position
	 *            the position to set
	 */
	public final void setPosition(int position) {
		this.position = position;
	}

	/**
	 * @return the offset
	 */
	public final int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public final void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return the length
	 */
	public final int getLength() {
		return length;
	}

	/**
	 * @param length
	 *            the length to set
	 */
	public final void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return the field
	 */
	public final Field getField() {
		return field;
	}

	/**
	 * @return the unique
	 */
	public final boolean isUnique() {
		return unique;
	}

	/**
	 * @param unique
	 *            the unique to set
	 */
	public final void setUnique(boolean unique) {
		this.unique = unique;
	}

	/**
	 * @return the primaryKey
	 */
	public final boolean isPrimaryKey() {
		return primaryKey;
	}

	/**
	 * @param primaryKey
	 *            the primaryKey to set
	 */
	public final void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	/**
	 * @return the identity
	 */
	public final boolean isIdentity() {
		return identity;
	}

	/**
	 * @param identity
	 *            the identity to set
	 */
	public final void setIdentity(boolean identity) {
		this.identity = identity;
	}

	/**
	 * @return the generated
	 */
	public final boolean isGenerated() {
		return generated;
	}

	/**
	 * @param generated
	 *            the generated to set
	 */
	public final void setGenerated(boolean generated) {
		this.generated = generated;
	}

	public final String getFieldName() {
		return this.field.getName();
	}

	/**
	 * @return the trans
	 */
	public final boolean isTransient() {
		return trans;
	}

	/**
	 * @param trans the trans to set
	 */
	public final void setTransient(boolean trans) {
		this.trans = trans;
	}

	
}
