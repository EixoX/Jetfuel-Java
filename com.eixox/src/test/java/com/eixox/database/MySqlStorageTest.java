package com.eixox.database;

import com.eixox.data.TestEntity1;

public class MySqlStorageTest extends DatabaseStorageTest {

	@Override
	protected DatabaseStorage<TestEntity1> createStorage(Class<TestEntity1> claz) {
		return MySqlStorage.getInstance(claz);
	}

}
