package com.eixox.database;

import java.util.HashMap;
import java.util.Properties;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityStorage;

public class MySqlStorage<T> extends EntityStorage<T> {

	private static final HashMap<Class<?>, MySqlStorage<?>> instances = new HashMap<Class<?>, MySqlStorage<?>>();

	private static final Properties buildProperties() {
		Properties props = new Properties();
		props.put("user", "root");
		return props;
	}

	private static final String url = "jdbc:mysql://localhost:3306/test";

	private MySqlStorage(Class<T> claz) {
		super(
				EntityAspect.getDefaultInstance(claz),
				new MySqlDatabase(url, buildProperties()));
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
