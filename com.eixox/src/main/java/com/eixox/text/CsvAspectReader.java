package com.eixox.text;

import java.io.BufferedReader;
import java.util.Iterator;

public class CsvAspectReader<T> implements Iterable<T>, Iterator<T> {

	private final CsvReader reader;
	private final CsvAspect<T> aspect;
	private String[] row;
	private int[] ordinals;

	public CsvAspectReader(CsvAspect<T> aspect, CsvReader reader) {
		this.reader = reader;
		this.aspect = aspect;
		this.ordinals = new int[reader.getHeaderCount()];
		int foundHeaders = 0;
		for (int i = 0; i < this.ordinals.length; i++) {
			this.ordinals[i] = reader.getOrdinal(aspect.get(i).getDataName());
			if (this.ordinals[i] >= 0)
				foundHeaders++;
		}
		if (foundHeaders <= 0)
			throw new RuntimeException("Ops, no headers found for schema " + aspect.getDataType());
	}

	public CsvAspectReader(CsvAspect<T> aspect, BufferedReader reader) {
		this(aspect, new CsvReader(reader, aspect.getCulture(), aspect.getSeparator()));
	}

	public boolean hasNext() {
		this.row = reader.readRow();
		return this.row != null;
	}

	public final T next() {
		// System.out.println(Strings.join(",", this.row));
		@SuppressWarnings("unchecked")
		T entity = (T) aspect.newInstance();
		for (int i = 0; i < ordinals.length; i++)
			if (ordinals[i] >= 0 && this.row.length > ordinals[i])
				aspect.get(ordinals[i]).setCsvValue(entity, this.row[i]);
		return entity;
	}

	public final void remove() {
		// No removing!

	}

	public final Iterator<T> iterator() {
		return this;
	}

	public final void close() {
		this.reader.close();
	}
}
