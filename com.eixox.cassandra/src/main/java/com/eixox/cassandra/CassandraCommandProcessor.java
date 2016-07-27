package com.eixox.cassandra;

import com.datastax.driver.core.ResultSet;

public interface CassandraCommandProcessor<T> {

	public T process(ResultSet rs);
}
