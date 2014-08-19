package com.eixox.data.entities;

import com.eixox.data.ColumnType;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class EntityAspectMember extends AbstractAspectMember {

	public final boolean nullable;
	public final ColumnType columntType;
	public final String columnName;
	public final boolean readOnly;

	public EntityAspectMember(AspectMember member, ColumnType columnType, String columnName, boolean nullable, boolean readOnly) {
		super(member);
		this.nullable = nullable;
		this.columntType = columnType;
		this.columnName = columnName == null || columnName.isEmpty() ? member.getName() : columnName;
		this.readOnly = readOnly;
	}

}
