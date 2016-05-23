package com.eixox.mongodb;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;

import com.eixox.data.Select;
import com.eixox.data.SelectResult;
import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;
import com.eixox.reflection.AspectMember;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSelect extends Select {

	public final MongoDatabase db;
	public final String from;

	public MongoSelect(MongoDatabase db, String collectionName) {
		this.db = db;
		this.from = collectionName;
	}

	@Override
	public final long count() {
		MongoCollection<BasicDBObject> collection = this.db.getCollection(this.from, BasicDBObject.class);
		return this.filter == null ? collection.count() : collection.count(MongoDialect.buildQuery(this.filter));
	}

	@Override
	public boolean exists() {
		MongoCollection<BasicDBObject> collection = this.db.getCollection(this.from, BasicDBObject.class);
		return this.filter == null
				? (collection.find().first() != null)
				: (collection.find(MongoDialect.buildQuery(this.filter)).first() != null);
	}

	private final FindIterable<Document> buildCursor() {

		MongoCollection<Document> collection = this.db.getCollection(this.from);

		FindIterable<Document> find = this.filter == null
				? collection.find()
				: collection.find(MongoDialect.buildQuery(this.filter));

		if (this.sort != null)
			find = find.sort(MongoDialect.buildSort(this.sort));

		if (this.offset > 0)
			find = find.skip(this.offset);

		if (this.limit > 0)
			find = find.limit(limit);

		return find;
	}

	@Override
	public SelectResult toResult() {
		SelectResult result = new SelectResult();
		for (Document doc : buildCursor()) {
			for (String key : doc.keySet()) {
				if (result.getOrdinal(key) < 0)
					result.columns.add(key);
			}

			Object[] row = new Object[result.columns.size()];
			for (int i = 0; i < row.length; i++)
				row[i] = doc.get(result.columns.get(i));
			result.rows.add(row);
		}
		return result;
	}

	@Override
	public Object scalar() {
		Document doc = buildCursor().first();
		return doc == null ? null : doc.get(doc.keySet().iterator().next());
	}

	@Override
	public Object[] first() {
		Document doc = buildCursor().first();
		throw new RuntimeException("NOT IMPLEMENTED");
	}

	@Override
	public <T> T first(EntityAspect<T> aspect) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> int transform(EntityAspect<T> aspect, List<T> target) {
		// TODO Auto-generated method stub
		return 0;
	}

}
