package com.eixox.database;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.MySqlStorage;
import com.eixox.TestEntity1;

public class MySqlStorageTest {

	private MySqlStorage<TestEntity1>	storage;

	public final synchronized MySqlStorage<TestEntity1> getStorage() {
		if (storage == null)
			storage = MySqlStorage.getInstance(TestEntity1.class);
		return storage;
	}

	@Test
	public void getStorageTest() {
		MySqlStorage<TestEntity1> storage = getStorage();
		Assert.assertNotNull("Storage should not be null", storage);
	}

	@Test
	public void saveTest() {
		TestEntity1 entity = new TestEntity1();
		entity.email = "rodrigo.p.o.rtel@gmail.com";
		entity.cpf = 22136532839L;
		entity.dateCreated = new Date();
		getStorage().save(entity);
		Assert.assertTrue("The save method should have run gracefully", true);
	}

	@Test
	public void saveTest2() {
	}

	@Test
	public void deleteTest() {
	}

	@Test
	public void deleteTest2() {
	}

	@Test
	public void insertTest() {
	}

	@Test
	public void insertTest2() {
	}

	@Test
	public void existsTest() {
	}

	@Test
	public void updateTest() {
	}

	@Test
	public void updateTest2() {
	}

	@Test
	public void selectTest() {
	}

	@Test
	public void readByIdentityTest() {
	}

	@Test
	public void updateByMemberTest() {
	}

	@Test
	public void updateByMenberTest() {
	}

	@Test
	public void executeInsertAndScopeIdentityTest() {
	}

	@Test
	public void readMemberWhereTest() {
	}

	@Test
	public void readMemberWhereTest2() {
	}

	@Test
	public void readMemberWhereTest3() {
	}

	@Test
	public void readMemberWhereTest4() {
	}

	@Test
	public void readMemberWhereTest5() {
	}

	@Test
	public void readMemberWhereTest6() {
	}

	@Test
	public void executeSelectMemberTest() {
	}

	@Test
	public void executeSelectMemberTest2() {
	}

	@Test
	public void executeSelectMemberTest3() {
	}

	@Test
	public void executeSelectMemberTest4() {
	}

	@Test
	public void executeDeleteTest() {
	}

	@Test
	public void executeSelectTest() {
	}

	@Test
	public void executeSelectTest2() {
	}

	@Test
	public void executeSelectTest3() {
	}

	@Test
	public void executeSelectTest4() {
	}

	@Test
	public void executeInsertTest() {
	}

	@Test
	public void countWhereTest() {
	}

	@Test
	public void countWhereTest2() {
	}

	@Test
	public void countWhereTest3() {
	}

	@Test
	public void countWhereTest4() {
	}

	@Test
	public void countWhereTest5() {
	}

	@Test
	public void readWhereTest() {
	}

	@Test
	public void readWhereTest2() {
	}

	@Test
	public void readWhereTest3() {
	}

	@Test
	public void readWhereTest4() {
	}

	@Test
	public void readWhereTest5() {
	}

	@Test
	public void readWhereTest6() {
	}

	@Test
	public void readWhereTest7() {
	}

	@Test
	public void getAspectTest() {
	}

	@Test
	public void executeUpdateTest() {
	}

	@Test
	public void existsWhereTest() {
	}

	@Test
	public void existsWhereTest2() {
	}

	@Test
	public void existsWhereTest3() {
	}

	@Test
	public void existsWhereTest4() {
	}

	@Test
	public void existsWhereTest5() {
	}
}
