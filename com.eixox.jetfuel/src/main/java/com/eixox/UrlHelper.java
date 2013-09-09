package com.eixox;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;

public final class UrlHelper {

	public static final String downloadText(String url) throws IOException {
		String charset = "UTF-8";
		URLConnection connection = new URL(url).openConnection();
		connection.setDoOutput(true); // Triggers POST.
		connection.setRequestProperty("Accept-Charset", charset);
		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
			return StreamHelper.readText(inputStream, Charset.forName("UTF-8"));
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (IOException logOrIgnore) {
				}
		}
	}

	public static final HashMap<String, String> parseParameters(String encodedParameters) {
		if (encodedParameters == null || encodedParameters.isEmpty())
			return null;

		HashMap<String, String> hashmap = new HashMap<String, String>();
		String[] keyvaluePairs = encodedParameters.split("&");
		for (int i = 0; i < keyvaluePairs.length; i++) {
			String[] keyvalues = keyvaluePairs[i].split("=");
			if (keyvalues.length > 0) {
				if (keyvalues.length == 1)
					hashmap.put(keyvalues[0], keyvalues[0]);
				else
					hashmap.put(keyvalues[0], keyvalues[1]);
			}
		}

		return hashmap;
	}

	public static final String friendlyfy(String url) {
		if (url == null || url.isEmpty())
			return url;

		StringBuilder builder = new StringBuilder(url.length());
		boolean isPreviousNonLetterOrDigit = false;
		for (int i = 0; i < url.length(); i++)
			if (Character.isLetterOrDigit(url.charAt(i))) {
				builder.append(url.charAt(i));
				isPreviousNonLetterOrDigit = false;
			} else if (!isPreviousNonLetterOrDigit && i < (url.length() - 1)) {
				builder.append('-');
				isPreviousNonLetterOrDigit = true;
			}
		return builder.toString();
	}
}
