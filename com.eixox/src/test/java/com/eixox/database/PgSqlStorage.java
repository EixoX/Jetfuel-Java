package com.eixox.database;

import java.util.HashMap;
import java.util.Properties;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityStorage;

public class PgSqlStorage<T> extends EntityStorage<T> {

	private static final HashMap<Class<?>, PgSqlStorage<?>> instances = new HashMap<Class<?>, PgSqlStorage<?>>();

	private static final Properties buildProperties() {
		Properties props = new Properties();
		props.put("user", "postgres");
		props.put("password", "popopo6");
		return props;
	}

	private static final String url = "jdbc:postgresql://localhost/test";

	private PgSqlStorage(Class<T> claz) {
		super(
				EntityAspect.getDefaultInstance(claz),
				new PostgresDatabase(url, buildProperties()));
	}

	@SuppressWarnings("unchecked")
	public static synchronized final <T> PgSqlStorage<T> getInstance(Class<T> claz) {
		PgSqlStorage<T> storage = (PgSqlStorage<T>) instances.get(claz);
		if (storage == null) {
			storage = new PgSqlStorage<T>(claz);
			instances.put(claz, storage);
		}
		return storage;
	}
}
