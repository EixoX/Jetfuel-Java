package com.eixox.data.text;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;

public class TextDataWriter implements AutoCloseable {

	public final BufferedWriter writer;
	public final TextSchema<?> schema;
	public Object[] row;

	public int lineNumber = -1;

	public TextDataWriter(Writer in, TextSchema<?> schema) {
		this.writer = new BufferedWriter(in);
		this.schema = schema;
	}

	public void close() throws Exception {
		this.writer.close();
	}

	public boolean write() throws IOException {
		String line = schema.formatRow(this.row);
		writer.write(line);
		writer.newLine();
		lineNumber++;
		return true;
	}

}
