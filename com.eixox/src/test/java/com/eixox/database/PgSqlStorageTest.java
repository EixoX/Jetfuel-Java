package com.eixox.database;

import com.eixox.data.TestEntity1;

public class PgSqlStorageTest extends DatabaseStorageTest {

	@Override
	protected DatabaseStorage<TestEntity1> createStorage(Class<TestEntity1> claz) {
		return PgSqlStorage.getInstance(claz);
	}

}
