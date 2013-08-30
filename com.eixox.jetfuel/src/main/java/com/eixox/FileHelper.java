package com.eixox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

public class FileHelper {

	public static String readText(String fileName, Charset charset) throws IOException {

		File file = new File(fileName);
		if (!file.exists())
			return "";
		else {
			FileInputStream fis = new FileInputStream(file);
			return StreamHelper.readText(fis, charset);
		}
	}
}
