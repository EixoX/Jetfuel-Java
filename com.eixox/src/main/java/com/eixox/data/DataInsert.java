package com.eixox.data;

import java.util.ArrayList;

public abstract class DataInsert extends ArrayList<Object[]> {

	private static final long serialVersionUID = -4056016821435691405L;
	public final String from;
	public final ArrayList<String> cols = new ArrayList<String>();

	public abstract long execute();

	public abstract Object executeAndScopeIdentity(String identityName);

	public DataInsert(String into) {
		this.from = into;
	}
}
