package com.eixox.database;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.TestEntity1;
import com.eixox.data.entities.EntityStorage;

public abstract class DatabaseStorageTest {

	protected abstract EntityStorage<TestEntity1> createStorage(Class<TestEntity1> claz);

	@Test()
	public void createStorageInstanceTest() {
		EntityStorage<TestEntity1> storage = createStorage(TestEntity1.class);
		Assert.assertNotNull(storage);
	}

	@Test
	public void singleInsertTest() {
		TestEntity1 entity = new TestEntity1();
		entity.name = "Rodrigo Portela";
		entity.email = "rodrigo.portela@gmail.com";
		entity.cpf = 123123123;
		entity.birthDay = new GregorianCalendar(1980, 4, 12).getTime();
		entity.dateCreated = new Date();

		createStorage(TestEntity1.class).insert(entity);
		Assert.assertTrue(entity.id > 0);

	}

	@Test
	public void selectListTest() {
		List<TestEntity1> list = createStorage(TestEntity1.class).select().toList();
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void selectSingleResultTest() {
		TestEntity1 singleResult = createStorage(TestEntity1.class).select().first();
		Assert.assertNotNull(singleResult);
	}

	@Test
	public void selectCountTest() {
		long count = createStorage(TestEntity1.class).select().count();
		Assert.assertTrue(count > 0);
	}

	@Test
	public void selectExistsTest() {
		boolean exists = createStorage(TestEntity1.class).select().where("email", "rodrigo.portela@gmail.com").exists();
		Assert.assertTrue(exists);
	}

	@Test
	public void selectExistsWithBadFormatTest() {
		boolean exists = createStorage(TestEntity1.class).select().where("email", "rodrigo.portela'''@gmail.com").exists();
		Assert.assertFalse(exists);
	}

	@Test
	public void updateByIdentityTest() {
		EntityStorage<TestEntity1> storage = createStorage(TestEntity1.class);
		TestEntity1 entity = storage.select().where("email", "rodrigo.portela@gmail.com").first();
		if (entity != null) {
			entity.cpf = 321312123L;
			entity.dateUpdated = new Date();
			Assert.assertTrue(storage.update(entity) > 0);
		}
	}

	@Test
	public void deleteByIdentityTest() {
		EntityStorage<TestEntity1> storage = createStorage(TestEntity1.class);
		TestEntity1 entity = storage.select().where("cpf", 123123123L).first();
		if (entity != null) {
			Assert.assertTrue(storage.delete(entity) > 0);
		}
	}

	

}
