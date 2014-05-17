package com.eixox.database;

import java.util.HashMap;
import java.util.Properties;

import com.eixox.database.DatabaseStorage;
import com.eixox.database.MySqlDialect;

public class MySqlStorage<T> extends DatabaseStorage<T> {

	private static final HashMap<Class<?>, MySqlStorage<?>> instances = new HashMap<Class<?>, MySqlStorage<?>>();

	private static final Properties buildProperties() {
		Properties props = new Properties();
		props.put("user", "root");
		return props;
	}

	private MySqlStorage(Class<T> claz) {
		super(claz, "jdbc:mysql://localhost:3306/test", buildProperties(), new MySqlDialect());
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
