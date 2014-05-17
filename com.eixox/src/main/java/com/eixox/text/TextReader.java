package com.eixox.text;

import java.util.Date;
import java.util.UUID;

import com.eixox.globalization.Culture;

public abstract class TextReader {

	private Culture culture;
	private String[] row;

	protected abstract String[] readRow();

	public TextReader(Culture culture) {
		this.culture = culture;
	}

	public final Culture getCulture() {
		return culture;
	}

	public final void setCulture(Culture culture) {
		this.culture = culture;
	}

	public final String[] getRow() {
		return row;
	}

	public final void setRow(String[] row) {
		this.row = row;
	}

	public final boolean read() {
		this.row = readRow();
		return this.row != null;
	}

	public final boolean hasRow() {
		return this.row != null;
	}

	public abstract int getOrdinal(String name);

	public final int getOrdinalOrException(String name) {
		int ordinal = getOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException(name + " is not a column in this reader.");
		else
			return ordinal;
	}

	public final String getString(int ordinal) {
		return this.row[ordinal];
	}

	public final String getString(String name) {
		return this.row[this.getOrdinalOrException(name)];
	}

	public final boolean getBoolean(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? false : Boolean.parseBoolean(s);
	}

	public final boolean getBoolean(String name) {
		return getBoolean(getOrdinalOrException(name));
	}

	public final char getChar(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? (char) 0 : s.charAt(0);
	}

	public final char getChar(String name) {
		return getChar(getOrdinalOrException(name));
	}

	public final byte getByte(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? 0 : Byte.parseByte(s);
	}

	public final byte getByte(String name) {
		return getByte(getOrdinalOrException(name));
	}

	public final short getShort(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? 0 : Short.parseShort(s);
	}

	public final short getShort(String name) {
		return getShort(getOrdinalOrException(name));
	}

	public final int getInt(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? 0 : Integer.parseInt(s);
	}

	public final int getInt(String name) {
		return getInt(getOrdinalOrException(name));
	}

	public final long getLong(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? 0L : Long.parseLong(s);
	}

	public final long getLong(String name) {
		return getLong(getOrdinalOrException(name));
	}

	public final Number getNumber(int ordinal) {
		return this.culture.parseNumber(this.row[ordinal]);
	}

	public final Number getNumber(String name) {
		return getNumber(getOrdinalOrException(name));
	}

	public final Number getPercent(int ordinal) {
		return this.culture.parsePercent(this.row[ordinal]);
	}

	public final Number getPercent(String name) {
		return getPercent(getOrdinalOrException(name));
	}

	public final double getDouble(int ordinal) {
		Number n = getNumber(ordinal);
		return n == null ? 0.0 : n.doubleValue();
	}

	public final double getDouble(String name) {
		return getDouble(getOrdinalOrException(name));
	}

	public final float getFloat(int ordinal) {
		Number n = getNumber(ordinal);
		return n == null ? 0F : n.floatValue();
	}

	public final float getFloat(String name) {
		return getFloat(getOrdinalOrException(name));
	}

	public final Date getDateTime(int ordinal) {
		return this.culture.parseDateTime(this.row[ordinal]);
	}

	public final Date getDateTime(String name) {
		return getDateTime(getOrdinalOrException(name));
	}

	public final Date getLongDate(int ordinal) {
		return this.culture.parseLongDate(this.row[ordinal]);
	}

	public final Date getLongDate(String name) {
		return getLongDate(getOrdinalOrException(name));
	}

	public final Date getShortDate(int ordinal) {
		return this.culture.parseShortDate(this.row[ordinal]);
	}

	public final Date getShortDate(String name) {
		return getShortDate(getOrdinalOrException(name));
	}

	public final UUID getUUID(int ordinal) {
		String s = this.row[ordinal];
		return s == null || s.isEmpty() ? null : UUID.fromString(s);
	}

	public final UUID getUUID(String name) {
		return getUUID(getOrdinalOrException(name));
	}

}
