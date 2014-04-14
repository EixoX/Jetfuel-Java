package com.eixox.data;

import com.eixox.Strings;
import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public final class DataAspectMember extends AbstractAspectMember {

	private final String			dataName;
	private final boolean			nullable;
	private final ColumnType		columnType;
	private final ValueAdapter<?>	valueAdapter;

	public DataAspectMember(AspectMember member, Column column) {
		super(member);
		this.dataName = Strings.isNullOrEmptyAlternate(column.dataName(), member.getName());
		this.nullable = column.nullable();
		this.columnType = column.type();
		this.valueAdapter = ValueAdapters.getAdapter(member.getDataType());
		if (this.valueAdapter == null)
			throw new RuntimeException("Ops. We have no adapters for the type " + member.getDataType());
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

	@Override
	public final void setValue(Object instance, Object value) {
		value = this.valueAdapter.convert(value);
		super.setValue(instance, value);
	}
}
