package com.eixox.mongodb;

import com.eixox.data.DataDelete;
import com.eixox.data.DataInsert;
import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectMember;
import com.eixox.data.DataUpdate;
import com.eixox.data.Storage;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoStorage implements Storage {

	public final MongoDatabase db;
	public final MongoClient client;

	public MongoStorage(String serverAddress, String dbName) {
		this.client = new MongoClient(serverAddress);
		this.db = this.client.getDatabase(dbName);
	}

	public final DataSelect select(String name) {
		return new MongoSelect(this.db, name);
	}

	public final DataUpdate update(String name) {
		return new MongoUpdate(this.db, name);
	}

	public DataDelete delete(String name) {
		return new MongoDelete(this.db, name);
	}

	public DataInsert insert(String name) {
		return new MongoInsert(this.db, name);
	}

	public DataSelectMember selectMember(String collection, String member) {
		return new MongoSelectMember(this.db, collection, member);
	}

}
