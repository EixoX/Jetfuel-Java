package com.eixox.data;

import java.util.ArrayList;

public class SelectResult<T> extends ArrayList<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1458869597489472898L;
	private final int pageSize;
	private final int pageOrdinal;

	public SelectResult(int pageSize, int pageOrdinal) {
		super(pageSize > 100 ? pageSize / 4 : pageSize);
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final int getPageOrdinal() {
		return pageOrdinal;
	}

	
}
