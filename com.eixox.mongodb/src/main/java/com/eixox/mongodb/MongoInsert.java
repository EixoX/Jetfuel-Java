package com.eixox.mongodb;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.eixox.data.DataInsert;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoInsert extends DataInsert {

	public final MongoDatabase db;

	public MongoInsert(MongoDatabase db, String name) {
		super(name);
		this.db = db;
	}

	@Override
	public List<Object> execute() {

		MongoCollection<Document> col = db.getCollection(this.into);

		int colsize = this.cols.size();
		int rowsize = this.rows.size();

		ArrayList<Document> arrList = new ArrayList<Document>(rowsize);
		for (int k = 0; k < rowsize; k++) {
			Object[] row = this.rows.get(k);
			Document dbo = new Document();
			int imax = colsize > row.length ? row.length : colsize;
			for (int i = 0; i < imax; i++)
				dbo.put(this.cols.get(i), row[i]);
			arrList.add(dbo);
		}
		col.insertMany(arrList);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < arrList.size(); i++)
			list.add(arrList.get(i).get("_id"));
		return list;
	}

}
