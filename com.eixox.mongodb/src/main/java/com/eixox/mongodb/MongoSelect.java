package com.eixox.mongodb;

import java.util.List;

import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;
import com.eixox.data.entities.EntityAspect;
import com.eixox.reflection.AspectMember;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoSelect extends DataSelect {

	public final MongoDatabase db;

	public MongoSelect(MongoDatabase db, String collectionName) {
		super(collectionName);
		this.db = db;
	}

	@Override
	public DataSelectResult toResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public final long count() {
		MongoCollection<BasicDBObject> collection = this.db.getCollection(this.from, BasicDBObject.class);
		return this.filter == null ? collection.count() : collection.count(MongoDialect.buildQuery(this.filter));
	}

	@Override
	public boolean exists() {
		MongoCollection<BasicDBObject> collection = this.db.getCollection(this.from, BasicDBObject.class);
		return this.filter == null ? (collection.find().first() != null)
				: (collection.find(MongoDialect.buildQuery(this.filter)).first() != null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> long transform(final EntityAspect aspect, final List<T> list) {

		MongoCollection<BasicDBObject> collection = this.db.getCollection(this.from, BasicDBObject.class);
		FindIterable<BasicDBObject> cursor = null;
		int start = list.size();

		if (this.filter == null)
			cursor = collection.find();
		else
			cursor = collection.find(MongoDialect.buildQuery(this.filter));

		if (this.sort != null)
			cursor.sort(MongoDialect.buildSort(this.sort));

		if (this.pageSize > 0) {
			if (this.pageOrdinal > 0)
				cursor.skip(this.pageSize * this.pageOrdinal);

			cursor.limit(this.pageSize);
		}

		cursor.forEach(new Block<BasicDBObject>() {
			public void apply(BasicDBObject arg0) {
				T child = (T) aspect.newInstance();
				for (AspectMember member : aspect) {
					Object value = arg0.get(member.getName());
					if (value != null)
						member.setValue(child, value);
				}
				list.add(child);
			}
		});

		return list.size() - start;

	}

	@Override
	public <T> T toEntity(EntityAspect aspect) {
		// TODO Auto-generated method stub
		return null;
	}

}
