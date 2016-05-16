package com.eixox.mongodb;

import org.bson.Document;

import com.eixox.data.DataDelete;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

public class MongoDelete extends DataDelete {

	private final MongoDatabase db;

	public MongoDelete(MongoDatabase db, String from) {
		super(from);
		this.db = db;
	}

	@Override
	public long execute() {

		MongoCollection<Document> collection = db.getCollection(this.from);
		Document query = this.filter == null ? null : MongoDialect.buildQuery(this.filter);
		DeleteResult result = collection.deleteMany(query);
		return result.getDeletedCount();

	}

}
