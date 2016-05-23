package com.eixox.mongodb;

import org.bson.Document;

import com.eixox.data.Delete;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoDelete extends Delete {

	private final MongoDatabase db;
	private final String from;

	public MongoDelete(MongoDatabase db, String from) {
		this.db = db;
		this.from = from;
	}

	@Override
	public long execute() {

		MongoCollection<Document> collection = db.getCollection(this.from);
		Document query = this.where == null ? null : MongoDialect.buildQuery(this.where);
		DeleteResult result = collection.deleteMany(query);
		return result.getDeletedCount();

	}

}
