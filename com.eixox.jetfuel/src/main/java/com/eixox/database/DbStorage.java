package com.eixox.database;

import java.util.HashMap;

import com.eixox.data.ClassStorage;

public class DbStorage {

	private final DbStorageEngine engine;
	private final HashMap<Class<?>, ClassStorage<?>> instances = new HashMap<Class<?>, ClassStorage<?>>();

	public DbStorage(DbStorageEngine engine) {
		this.engine = engine;
	}

	public final DbStorageEngine getEngine() {
		return this.engine;
	}

	@SuppressWarnings("unchecked")
	public final synchronized <T> ClassStorage<T> getInstance(Class<T> claz) {
		ClassStorage<?> aspect = instances.get(claz);
		if (aspect == null) {
			aspect = new ClassStorage<T>(claz, this.engine);
			instances.put(claz, aspect);
		}
		return (ClassStorage<T>) aspect;
	}

}
