package com.eixox.mongodb;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityStorage;

public class MongoTests {

	@Test
	public void testSelect() {

		MongoStorage storage = new MongoStorage("127.0.0.1", "test");
		EntityStorage<MongoTestsEntity> db = new EntityStorage<MongoTestsEntity>(
				EntityAspect.getDefaultInstance(MongoTestsEntity.class), storage);

		MongoTestsEntity te1 = new MongoTestsEntity();
		te1.nome = "Guigui 2";
		db.insert(te1);

		List<MongoTestsEntity> list = db.select().toList();
		Assert.assertTrue(list.size() > 0);

	}
}
