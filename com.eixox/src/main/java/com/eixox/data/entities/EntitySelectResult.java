package com.eixox.data.entities;

import java.util.ArrayList;

public class EntitySelectResult<T> {

	public final long recordCount;
	public final long pageCount;
	public final int pageOrdinal;
	public final int pageSize;
	public final ArrayList<T> items;

	public EntitySelectResult(long recordCount, int pageSize, int pageOrdinal) {
		this.recordCount = recordCount;
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
		this.pageCount = pageSize > 0 ?
				(recordCount / pageSize) + 1L :
				1L;
		if (pageSize > 0)
			this.items = new ArrayList<T>(pageSize);
		else
			this.items = new ArrayList<T>();
	}

}
