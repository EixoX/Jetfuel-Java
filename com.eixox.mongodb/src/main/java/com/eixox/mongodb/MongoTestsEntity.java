package com.eixox.mongodb;

import org.bson.types.ObjectId;

import com.eixox.data.ColumnType;
import com.eixox.data.entities.Persistent;

@Persistent(name = "TestEntity")
public class MongoTestsEntity {

	@Persistent
	public String nome;

	@Persistent(type = ColumnType.IDENTITY)
	public ObjectId key;
}
