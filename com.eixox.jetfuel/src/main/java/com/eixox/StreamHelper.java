package com.eixox;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public final class StreamHelper {

	public static final String readText(InputStream is, Charset charset) throws IOException {

		byte[] buffer = new byte[4096];
		StringBuilder builder = new StringBuilder(4096);
		int read = is.read(buffer);

		while (read > 0) {
			String item = new String(buffer, 0, read, charset);
			builder.append(item);
			read = is.read(buffer);
		}
		return builder.toString();

	}
}
