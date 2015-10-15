package com.eixox.mongodb;

import java.net.UnknownHostException;

import com.eixox.data.DataDelete;
import com.eixox.data.DataInsert;
import com.eixox.data.DataSelect;
import com.eixox.data.DataUpdate;
import com.eixox.data.Storage;
import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoStorage implements Storage {

	public final DB db;
	public final MongoClient client;

	public MongoStorage(String serverAddress, String dbName) {
		try {
			this.client = new MongoClient(serverAddress);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
		this.db = this.client.getDB(dbName);
	}

	public final DataSelect select(String name) {
		return new MongoSelect(this.db, name);
	}

	public final DataUpdate update(String name) {
		return new MongoUpdate(this.db, name);
	}

	public DataDelete delete(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public DataInsert insert(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
