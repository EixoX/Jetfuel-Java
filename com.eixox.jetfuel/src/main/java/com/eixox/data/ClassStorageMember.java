package com.eixox.data;

import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;

public class ClassStorageMember extends DecoratedMember {

	private final String dataName;
	private final boolean nullable;
	private final ColumnType columnType;
	
	public ClassStorageMember(AspectMember member, Column column) {
		super(member);
		this.dataName = column.dataName().isEmpty() ? member.getName(): column.dataName();
		this.columnType = column.type();
		this.nullable = column.nullable();
	}

	public final String getDataName() {
		return dataName;
	}

	public final boolean isNullable() {
		return nullable;
	}

	public final ColumnType getColumnType() {
		return columnType;
	}

	

}
