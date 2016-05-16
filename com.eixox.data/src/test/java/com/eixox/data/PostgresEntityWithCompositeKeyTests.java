package com.eixox.data;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityStorage;
import com.eixox.database.PostgresDatabase;
import com.eixox.database.TableStorage;

public class PostgresEntityWithCompositeKeyTests {

	private final PostgresDatabase createDatabase()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Properties props = new Properties();
		props.put("user", "pgsa");
		props.put("password", "s3cr3t0");
		return new PostgresDatabase("jdbc:postgresql://localhost/test", props);
	}

	private final <T> EntityStorage<T> createStorage(Class<T> claz)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityAspect<T> aspect = new EntityAspect<T>(claz);
		TableStorage storage = new TableStorage(createDatabase(), aspect.tableName);
		return new EntityStorage<T>(storage, aspect);
	}

	@Test
	public void test_1_Insert100()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);

		// delete everything
		storage.delete().execute();

		ArrayList<EntityWithCompositeKey> insert = new ArrayList<EntityWithCompositeKey>();

		for (int i = 0; i < 100; i++) {
			EntityWithCompositeKey entity = new EntityWithCompositeKey();
			entity.key1 = i;
			entity.key2 = "a";
			entity.created_at = new Timestamp((new Date()).getTime());
			insert.add(entity);
		}

		storage.insert(insert);

		Assert.assertTrue(true);

	}

	@Test
	public void test_2_Insert1000()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);

		// delete everything
		storage.delete().execute();

		ArrayList<EntityWithCompositeKey> insert = new ArrayList<EntityWithCompositeKey>();

		for (int i = 0; i < 100; i++)
			for (int j = 0; j < 10; j++) {
				EntityWithCompositeKey entity = new EntityWithCompositeKey();
				entity.key1 = i;
				entity.key2 = Integer.toString(j);
				entity.created_at = new Timestamp((new Date()).getTime());
				insert.add(entity);
			}

		storage.insert(insert);

		Assert.assertTrue(true);

	}

	@Test
	public void test_3_Update()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);

		EntityWithCompositeKey entity = new EntityWithCompositeKey();
		entity.key1 = 0;
		entity.key2 = "1";
		entity.col3 = "Col3";
		entity.created_at = new Timestamp((new Date()).getTime());

		storage.update(entity);
		Assert.assertTrue(true);

	}

	@Test
	public void test_4_SelectUpdate()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);

		EntityWithCompositeKey entity = storage.selectByCompositeKeys(1, "1");
		entity.col3 = "Col4";
		storage.update(entity);
		Assert.assertTrue(true);

	}

	@Test
	public void test_5_Select()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);
		List<EntityWithCompositeKey> list = storage
				.select()
				.orderBy("key1")
				.thenOrderBy("key2")
				.limit(50)
				.toList();
		Assert.assertTrue(list.size() == 50);
	}

	@Test
	public void test_6_Save()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithCompositeKey> storage = createStorage(EntityWithCompositeKey.class);
		long count = storage.select().count();
		List<EntityWithCompositeKey> list = storage
				.select()
				.orderBy("key1")
				.thenOrderBy("key2")
				.limit(2)
				.toList();

		Assert.assertTrue(list.size() == 2);

		// change item 2 to insert
		list.get(1).key2 = "NEW";
		list.get(1).created_at = new Timestamp(new Date().getTime());

		storage.save(list);

		Assert.assertTrue(storage.select().count() > count);
	}

}
