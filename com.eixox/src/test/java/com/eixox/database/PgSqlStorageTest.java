package com.eixox.database;

import com.eixox.data.TestEntity1;
import com.eixox.data.entities.EntityStorage;

public class PgSqlStorageTest extends DatabaseStorageTest {

	@Override
	protected EntityStorage<TestEntity1> createStorage(Class<TestEntity1> claz) {
		return PgSqlStorage.getInstance(claz);
	}

}
