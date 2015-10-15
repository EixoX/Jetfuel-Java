package com.eixox.mongodb;

import com.eixox.data.DataUpdate;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class MongoUpdate extends DataUpdate {

	private final DB db;

	public MongoUpdate(DB db, String name) {
		super(name);
		this.db = db;
	}

	@Override
	public long execute() {
		DBCollection collection = db.getCollection(this.from);
		return 0;
	}

}
