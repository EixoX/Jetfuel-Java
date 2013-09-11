package com.eixox.data;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;

public class ClassStorageMember extends DecoratedMember {

	private final String dataName;
	private final boolean nullable;
	private final ColumnType columnType;
	private final ValueAdapter<?> valueAdapter;

	public ClassStorageMember(AspectMember member, Column column) {
		super(member);
		this.dataName = column.dataName().isEmpty() ? member.getName() : column.dataName();
		this.columnType = column.type();
		this.nullable = column.nullable();
		this.valueAdapter = ValueAdapters.getAdapter(member.getType());
		if (this.valueAdapter == null) {
			throw new RuntimeException("Unable to adapt " + member.getType());
		}
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

	public final ValueAdapter<?> getValueAdapter() {
		return this.valueAdapter;
	}
}
