import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestEntity1PgSqlStorage {

	@Test()
	public void createStorageInstanceTest() {
		PgSqlTestStorage<TestEntity1> storage = PgSqlTestStorage.getInstance(TestEntity1.class);
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

		boolean result = PgSqlTestStorage.getInstance(TestEntity1.class).insert(entity);
		Assert.assertTrue(result && entity.id > 0);

	}

	@Test
	public void selectListTest() {
		List<TestEntity1> list = PgSqlTestStorage.getInstance(TestEntity1.class).select().toList();
		Assert.assertTrue(list.size() > 0);
	}

	@Test
	public void selectSingleResultTest() {
		TestEntity1 singleResult = PgSqlTestStorage.getInstance(TestEntity1.class).select().singleResult();
		Assert.assertNotNull(singleResult);
	}

	@Test
	public void selectCountTest() {
		long count = PgSqlTestStorage.getInstance(TestEntity1.class).selectCount(null);
		Assert.assertTrue(count > 0);
	}

	@Test
	public void selectExistsTest() {
		boolean exists = PgSqlTestStorage.getInstance(TestEntity1.class).select().where("email", "rodrigo.portela@gmail.com").exists();
		Assert.assertTrue(exists);
	}
	
	@Test
	public void selectExistsWithBadFormatTest() {
		boolean exists = PgSqlTestStorage.getInstance(TestEntity1.class).select().where("email", "rodrigo.portela'''@gmail.com").exists();
		Assert.assertFalse(exists);
	}

	@Test
	public void updateByIdentityTest() {
		PgSqlTestStorage<TestEntity1> instance = PgSqlTestStorage.getInstance(TestEntity1.class);
		TestEntity1 entity = instance.select().where("email", "rodrigo.portela@gmail.com").singleResult();
		if (entity != null) {
			entity.cpf = 321312123L;
			entity.dateUpdated = new Date();
			Assert.assertTrue(instance.update(entity) == 1);
		}
	}

	@Test
	public void deleteByIdentityTest() {
		PgSqlTestStorage<TestEntity1> instance = PgSqlTestStorage.getInstance(TestEntity1.class);
		TestEntity1 entity = instance.select().where("cpf", 123123123).singleResult();
		if (entity != null) {
			Assert.assertTrue(instance.delete(entity) == 1);
		}
	}
}
