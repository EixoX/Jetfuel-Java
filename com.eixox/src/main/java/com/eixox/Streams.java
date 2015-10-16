package com.eixox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Properties;

public final class Streams {

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

	public static synchronized final Properties loadProperties(Class<?> claz) {
		String fileName = claz.getSimpleName() + ".properties";
		Properties props = new Properties();
		File file = new File(fileName);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				try {
					props.load(fis);
				} finally {
					fis.close();
				}
			} catch (Exception ex) {
				// ignore exceptions
			}
		}
		return props;
	}

	public static synchronized final void saveProperties(Class<?> claz, Properties props) {
		String fileName = claz.getSimpleName() + ".properties";
		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file, false);
			try {
				props.store(fos, null);
			} finally {
				fos.close();
			}
		} catch (Exception ex) {
			// ignore exceptions
		}
	}
}
