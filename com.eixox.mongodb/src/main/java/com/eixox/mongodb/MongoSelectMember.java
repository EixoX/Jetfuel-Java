package com.eixox.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.eixox.data.DataSelectMember;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;

public class MongoSelectMember extends DataSelectMember {

	private final MongoDatabase db;

	public MongoSelectMember(MongoDatabase db, String from, String member) {
		super(from, member);
		this.db = db;
	}

	@Override
	public List<Object> toList() {
		List<Object> list = new ArrayList<Object>();
		FindIterable<Document> col = this.db
				.getCollection(this.from)
				.find(MongoDialect.buildQuery(this.filter))
				.sort(MongoDialect.buildSort(this.sort));

		if (this.pageSize > 0) {
			col = col.skip(this.pageSize * this.pageOrdinal).limit(this.pageSize);
		}

		for (Document doc : col) {
			list.add(doc.get(this.member));
		}
		return list;

	}

	@Override
	public long count() {
		return this.db
				.getCollection(this.from)
				.count(MongoDialect.buildQuery(this.filter));
	}

	@Override
	public boolean exists() {
		return this.db
				.getCollection(this.from)
				.find(MongoDialect.buildQuery(this.filter))
				.limit(1)
				.first() != null;
	}

	@Override
	public Object first() {
		return this.db
				.getCollection(this.from)
				.find(MongoDialect.buildQuery(this.filter))
				.sort(MongoDialect.buildSort(this.sort))
				.first()
				.get(this.member);
	}

}
