package com.eixox.data;

import java.util.ArrayList;

public abstract class Insert extends ArrayList<InsertRow> {

	private static final long serialVersionUID = -2934772731411836280L;
	public final ArrayList<String> columns = new ArrayList<String>();

	public final Insert add(Object[] row) {
		InsertRow ir = new InsertRow();
		ir.values = row;
		super.add(ir);
		return this;
	}

	public abstract void execute(boolean returningGeneratedKey);
}
