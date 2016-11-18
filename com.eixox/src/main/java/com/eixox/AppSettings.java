package com.eixox;

import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public final class AppSettings {

	private AppSettings() {
	}

	private static final Properties PROPERTIES;

	static {
		PROPERTIES = new Properties();
		//ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		try {
			PROPERTIES.loadFromXML(classLoader.getResourceAsStream("AppSettings.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getResourceUrl() throws MalformedURLException {
		URL root = AppSettings.class.getProtectionDomain().getCodeSource().getLocation();
		URL propFile = new URL(root, "/AppSettings.xml");
		return propFile.toString();
	}

	public static int count() {
		return PROPERTIES.size();
	}

	public static final String get(String key) {
		return PROPERTIES.getProperty(key);
	}

	public static final String get(String key, String defaultValue) {
		return PROPERTIES.getProperty(key, defaultValue);
	}

	public static synchronized final void set(String key, String value) {
		PROPERTIES.setProperty(key, value);
		String fileName = "AppSettings.xml";
		File file = new File(fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file, false);
			PROPERTIES.storeToXML(fos, "Eixo X App Settings File");
		} catch (Exception e) {
		}
	}
}
