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

public class PostgresEntityWithIdentity {

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

		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);

		// delete everything
		storage.delete().execute();

		ArrayList<EntityWithIdentity> insert = new ArrayList<EntityWithIdentity>();

		for (int i = 0; i < 100; i++) {
			EntityWithIdentity entity = new EntityWithIdentity();
			entity.col1 = Integer.toString(i);
			entity.created_at = new Timestamp((new Date()).getTime());
			insert.add(entity);
		}

		storage.insert(insert);

		Assert.assertTrue(true);

	}

	@Test
	public void test_2_Insert1000()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);

		// delete everything
		storage.delete().execute();

		ArrayList<EntityWithIdentity> insert = new ArrayList<EntityWithIdentity>();

		for (int i = 0; i < 100; i++)
			for (int j = 0; j < 10; j++) {
				EntityWithIdentity entity = new EntityWithIdentity();
				entity.col1 = Integer.toString(i * 10 + j);
				entity.created_at = new Timestamp((new Date()).getTime());
				insert.add(entity);
			}

		storage.insert(insert);

		Assert.assertTrue(true);

	}

	@Test
	public void test_3_Update()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);

		EntityWithIdentity entity = new EntityWithIdentity();
		entity.id = 10;
		entity.col1 = "UPDATED";
		entity.created_at = new Timestamp((new Date()).getTime());

		storage.update(entity);
		Assert.assertTrue(true);

	}

	@Test
	public void test_4_SelectUpdate()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);

		EntityWithIdentity entity = storage.select().first();
		entity.col1 = "UPDATED";
		storage.update(entity);
		Assert.assertTrue(true);

	}

	@Test
	public void test_5_Select()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);
		List<EntityWithIdentity> list = storage
				.select()
				.orderBy("created_at")
				.limit(50)
				.toList();
		Assert.assertTrue(list.size() == 50);
	}

	@Test
	public void test_6_Save()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {

		EntityStorage<EntityWithIdentity> storage = createStorage(EntityWithIdentity.class);
		long count = storage.select().count();
		List<EntityWithIdentity> list = storage
				.select()
				.orderBy("created_at")
				.limit(2)
				.toList();
		Assert.assertTrue(list.size() == 2);

		// change item 2 to insert
		list.get(1).id = 0;
		list.get(1).col1 = "NEW";
		list.get(1).created_at = new Timestamp(new Date().getTime());

		storage.save(list);

		Assert.assertTrue(storage.select().count() > count);
	}

}
