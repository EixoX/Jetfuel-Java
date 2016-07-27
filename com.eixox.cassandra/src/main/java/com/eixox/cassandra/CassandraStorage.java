package com.eixox.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.eixox.data.Delete;
import com.eixox.data.Insert;
import com.eixox.data.Select;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class CassandraStorage implements AutoCloseable, Storage {

	public final Session session;
	public final Cluster cluster;

	public CassandraStorage(Cluster cluster, String keyspace) {
		this.cluster = cluster;
		this.session = this.cluster.connect(keyspace);

	}

	public void close() throws Exception {
		session.close();
	}

	public Insert insert(String into) {
		return new CassandraInsert(session, into);
	}

	public Select select(String from) {
		return new CassandraSelect(session, from);
	}

	public Delete delete(String from) {
		return new CassandraDelete(session, from);
	}

	public Update update(String target) {
		return new CassandraUpdate(session, target);
	}

}
