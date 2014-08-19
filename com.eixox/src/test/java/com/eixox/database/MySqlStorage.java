package com.eixox.database;

import java.util.HashMap;
import java.util.Properties;

public class MySqlStorage<T> extends DatabaseStorage<T> {

	private static final HashMap<Class<?>, MySqlStorage<?>> instances = new HashMap<Class<?>, MySqlStorage<?>>();

	private static final Properties buildProperties() {
		Properties props = new Properties();
		props.put("user", "root");
		return props;
	}

	private MySqlStorage(Class<T> claz) {
		super(new MySqlDatabase("jdbc:mysql://localhost:3306/test", buildProperties()), claz);
	}

	@SuppressWarnings("unchecked")
	public static synchronized final <T> MySqlStorage<T> getInstance(Class<T> claz) {
		MySqlStorage<T> storage = (MySqlStorage<T>) instances.get(claz);
		if (storage == null) {
			storage = new MySqlStorage<T>(claz);
			instances.put(claz, storage);
		}
		return storage;
	}

}
