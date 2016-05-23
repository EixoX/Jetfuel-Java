package com.eixox.database;

import java.util.HashMap;
import java.util.Properties;

import com.eixox.data.entities.EntityStorage;

public class PgSqlStorage {

	private static final HashMap<Class<?>, EntityStorage<?>> instances;
	private static final Properties properties;
	private static final String url;
	private static final PostgresDatabase database;

	static {
		instances = new HashMap<Class<?>, EntityStorage<?>>();
		url = "jdbc:postgresql://localhost/test";
		properties = new Properties();
		properties.put("user", "postgres");
		properties.put("password", "popopo6");
		try {
			database = new PostgresDatabase(url, properties);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public static synchronized final <T> EntityStorage<T> getInstance(Class<T> claz) {
		EntityStorage<T> storage = (EntityStorage<T>) instances.get(claz);
		if (storage == null) {
			storage = database.createStorage(claz);
			instances.put(claz, storage);
		}
		return storage;
	}
}
