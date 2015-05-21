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

	public CsvAspectReader(InputStreamReader reader, CsvAspect<T> aspect) {
		this.reader = new BufferedReader(reader);
		this.aspect = aspect;
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

	public T next() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Iterator<T> iterator() {
		return this;
	}

}
