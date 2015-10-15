package com.eixox.mongodb;

import java.util.ArrayList;
import java.util.List;

import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;
import com.eixox.data.entities.EntityAspect;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MongoSelect extends DataSelect {

	public final DB db;

	public MongoSelect(DB db, String collectionName) {
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
		DBCollection collection = this.db.getCollection(this.from);
		return this.filter == null ? collection.count() : collection.count(MongoDialect.buildQuery(this.filter));
	}

	@Override
	public boolean exists() {
		DBCollection collection = this.db.getCollection(this.from);
		return this.filter == null ? (collection.findOne() != null) : (collection.findOne(MongoDialect.buildQuery(this.filter)) != null);
	}

	@Override
	public Object getFirstMember(String name) {
		DBCollection collection = this.db.getCollection(this.from);
		DBObject first = this.filter == null ? collection.findOne() : collection.findOne(MongoDialect.buildQuery(this.filter));
		return first == null ? null : first.get(name);
	}

	@Override
	public List<Object> getMember(String name) {
		List<Object> list = new ArrayList<Object>();
		DBCollection collection = this.db.getCollection(this.from);
		DBCursor cursor = this.filter == null ? collection.find() : collection.find(MongoDialect.buildQuery(this.filter));
		try {
			if (this.sort != null)
				cursor.sort(MongoDialect.buildSort(this.sort));

			if (this.pageSize > 0 && this.pageOrdinal > 0)
				cursor.skip(this.pageSize * this.pageOrdinal);

			for (int i = 0; cursor.hasNext() && (this.pageSize > 0 && i < this.pageSize); i++) {
				DBObject dbo = cursor.next();
				list.add(dbo.get(name));
			}
		} finally {
			cursor.close();
		}
		return list;

	}

	@Override
	public DataSelectResult getMembers(String... names) {

		DataSelectResult result = new DataSelectResult();
		for (int i = 0; i < names.length; i++)
			result.cols.add(names[i]);

		DBCollection collection = this.db.getCollection(this.from);
		DBCursor cursor = this.filter == null ? collection.find() : collection.find(MongoDialect.buildQuery(this.filter));
		try {
			if (this.sort != null)
				cursor.sort(MongoDialect.buildSort(this.sort));

			if (this.pageSize > 0 && this.pageOrdinal > 0)
				cursor.skip(this.pageSize * this.pageOrdinal);

			for (int i = 0; cursor.hasNext() && (this.pageSize > 0 && i < this.pageSize); i++) {
				DBObject dbo = cursor.next();
				Object[] row = new Object[names.length];
				for (int j = 0; j < row.length; j++)
					row[j] = dbo.get(names[j]);
				result.add(row);
			}
		} finally {
			cursor.close();
		}
		return result;
	}

	@Override
	public Object[] getFirstMembers(String... names) {
		Object[] row = new Object[names.length];
		DBCollection collection = this.db.getCollection(this.from);
		DBObject dbo = this.filter == null ? collection.findOne() : collection.findOne(MongoDialect.buildQuery(this.filter));
		if (dbo != null) {
			for (int j = 0; j < row.length; j++)
				row[j] = dbo.get(names[j]);
		}
		return row;
	}

	@Override
	public <T> long transform(EntityAspect aspect, List<T> list) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <T> T getEntity(EntityAspect aspect) {
		// TODO Auto-generated method stub
		return null;
	}

}
