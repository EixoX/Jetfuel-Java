package com.eixox.text;

import java.io.BufferedReader;
import java.io.Reader;

import com.eixox.globalization.Culture;

public class CsvReader extends TextReader {

	private final BufferedReader bufferedReader;
	private final String[] headers;
	private final String separator;

	public CsvReader(BufferedReader bufferedReader, Culture culture, String separator) {
		super(culture);
		this.bufferedReader = bufferedReader;
		this.separator = separator;
		try {
			String line = this.bufferedReader.readLine();
			this.headers = line.split(separator);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public CsvReader(Reader in, Culture culture, String separator) {
		this(new BufferedReader(in), culture, separator);
	}

	public final BufferedReader getBufferedReader() {
		return bufferedReader;
	}

	public final String[] getHeaders() {
		return headers;
	}

	public final int getHeaderCount() {
		return this.headers.length;
	}

	public final String getSeparator() {
		return separator;
	}

	public final void close() {
		try {
			this.bufferedReader.close();
		} catch (Exception ignored) {
			ignored.printStackTrace();
		}
	}

	@Override
	protected final String[] readRow() {
		try {
			String line = this.bufferedReader.readLine();
			return line == null || line.isEmpty() ? null : line.split(separator);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public final int getOrdinal(String name) {
		for (int i = 0; i < this.headers.length; i++)
			if (name.equalsIgnoreCase(this.headers[i]))
				return i;
		return -1;
	}

}
