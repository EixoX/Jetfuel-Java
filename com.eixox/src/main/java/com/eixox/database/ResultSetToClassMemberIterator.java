package com.eixox.database;

import java.sql.ResultSet;
import java.util.Iterator;

public final class ResultSetToClassMemberIterator implements Iterable<Object>, Iterator<Object> {

	private final ResultSet	rs;
	private boolean			next;

	public ResultSetToClassMemberIterator(ResultSet rs, DatabaseAspect aspect) {
		try {
			this.rs = rs;
			this.next = rs.next();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final boolean hasNext() {
		return this.next;
	}

	public final Object next() {
		try {
			Object value = rs.getObject(1);
			this.next = rs.next();
			return value;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final void remove() {
		throw new RuntimeException("You cannot remove from a result set iterator");
	}

	public final Iterator<Object> iterator() {
		return this;
	}

}
