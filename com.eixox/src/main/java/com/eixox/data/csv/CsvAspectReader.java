package com.eixox.data.csv;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Iterator;

import com.eixox.interceptors.EntityInterceptor;

public class CsvAspectReader<T> implements Iterable<T>, Iterator<T> {

	private final BufferedReader reader;
	private final CsvAspect<T> aspect;
	public EntityInterceptor<T> interceptor;
	private String line;
	public final String separators;
	public String[] row;

	public CsvAspectReader(InputStreamReader reader, CsvAspect<T> aspect, String separators) {
		this.reader = new BufferedReader(reader);
		this.aspect = aspect;
		this.separators = separators;
	}

	public boolean hasNext() {
		try {
			this.line = reader.readLine();
			if (this.line == null)
				return false;
			else if (this.line.isEmpty())
				return hasNext();
			else
				return true;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T next() {
		this.row = this.line.split(this.separators);
		T instance = (T) aspect.newInstance();
		for (int i = 0; i < row.length; i++)
			this.aspect.setValue(instance, i, this.row[i]);
		return interceptor == null ? instance : interceptor.intercept(instance);
	}

	public final Iterator<T> iterator() {
		return this;
	}

	public void remove() {
		throw new RuntimeException("can't remove!");

	}

}
