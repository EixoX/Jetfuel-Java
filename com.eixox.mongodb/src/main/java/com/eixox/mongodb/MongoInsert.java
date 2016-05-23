package com.eixox.mongodb;

import java.util.ArrayList;

import org.bson.Document;

import com.eixox.data.Insert;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoInsert extends Insert {

	private static final long serialVersionUID = -6756024025722833613L;
	public final MongoDatabase db;
	public final String into;

	public MongoInsert(MongoDatabase db, String name) {
		this.db = db;
		this.into = name;
	}

	@Override
	public void execute(boolean returningGeneratedKey) {
		MongoCollection<Document> col = db.getCollection(this.into);

		int colsize = this.columns.size();
		int rowsize = this.size();

		ArrayList<Document> arrList = new ArrayList<Document>(rowsize);
		for (int k = 0; k < rowsize; k++) {
			Object[] row = this.get(k).values;
			Document dbo = new Document();
			int imax = colsize > row.length ? row.length : colsize;
			for (int i = 0; i < imax; i++)
				dbo.put(this.columns.get(i), row[i]);
			arrList.add(dbo);
		}
		col.insertMany(arrList);
		if (returningGeneratedKey) {
			for (int i = 0; i < arrList.size(); i++) {
				this.get(i).generatedKey = arrList.get(i).get("_id");
			}
		}
	}

}
