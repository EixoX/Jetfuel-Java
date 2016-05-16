package com.eixox.data.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.List;

public class TextDataReader implements AutoCloseable, Iterable<Object[]>, Iterator<Object[]> {

	public final BufferedReader reader;
	public final TextSchema<?> schema;
	public String line;
	public Object[] row;

	public int lineNumber = -1;

	public TextDataReader(Reader in, TextSchema<?> schema) throws IOException {
		this.reader = new BufferedReader(in);
		this.schema = schema;
		for (int i = 0; i < schema.lineOffset(); i++) {
			this.line = this.reader.readLine();
			if (this.line != null)
				lineNumber++;
		}

	}

	public void close() throws Exception {
		this.reader.close();
	}

	public boolean read() throws IOException {
		return read(null);
	}

	public boolean read(TextFilter filter) throws IOException {
		this.line = reader.readLine();

		if (line == null)
			return false;

		this.lineNumber++;

		if (schema.ignoreBlankLines() && line.trim().isEmpty())
			return read(filter);

		if (schema.ignoreCommentLines() && line.startsWith(schema.getCommentQualifier()))
			return read(filter);

		this.row = this.schema.parseRow(this.line);

		if (filter != null && !filter.filter(this.row))
			return read(filter);
		else
			return true;
	}

	public Object[] getValues(List<String> columns) {
		Object[] vals = new Object[columns.size()];
		for (int i = 0; i < vals.length; i++) {
			int ordinal = schema.getOrdinal(columns.get(i));
			vals[i] = this.row[ordinal];
		}
		return vals;
	}

	public Object get(String name) {
		return this.row[schema.getOrdinal(name)];
	}

	public boolean hasNext() {
		try {
			return read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Object[] next() {
		return this.row;
	}

	public Iterator<Object[]> iterator() {
		return this;
	}

}
