package com.eixox.database;

import com.eixox.data.DataAspectMember;
import com.eixox.reflection.AspectMember;

public final class DatabaseAspectMember extends DataAspectMember {

	private final DatabaseColumnType columnType;

	public DatabaseAspectMember(AspectMember member, DatabaseColumn column) {
		super(member, column.dataName(), column.nullable(), column.caption());
		this.columnType = column.type();
	}

	public final DatabaseColumnType getColumnType() {
		return columnType;
	}

}
