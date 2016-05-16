package com.eixox.mongodb;

import org.bson.Document;

import com.eixox.Pair;
import com.eixox.data.DataUpdate;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

public class MongoUpdate extends DataUpdate {

	private final MongoDatabase db;

	public MongoUpdate(MongoDatabase db, String name) {
		super(name);
		this.db = db;
	}

	@Override
	public long execute() {

		MongoCollection<Document> collection = this.db.getCollection(this.from);

		Document upValues = new Document();
		for (Pair<String, Object> pair : this.values) {
			upValues.append("$set", new Document(pair.key, pair.value));
		}

		Document query = this.filter == null ? null : MongoDialect.buildQuery(this.filter);

		UpdateResult result = collection.updateMany(query, upValues);

		return result.getModifiedCount();

	}

}
