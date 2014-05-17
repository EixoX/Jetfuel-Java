package com.eixox.data;

import com.eixox.Strings;
import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class DataAspectMember extends AbstractAspectMember {

	private final String dataName;
	private final String caption;
	private final boolean nullable;
	private final ValueAdapter<?> valueAdapter;

	public DataAspectMember(AspectMember member, String dataName, boolean nullable, String caption) {
		super(member);
		this.dataName = Strings.isNullOrEmptyAlternate(dataName, member.getName());
		this.nullable = nullable;
		this.caption = Strings.isNullOrEmptyAlternate(caption, member.getName());
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

	public final String getCaption() {
		return this.caption;
	}

	public final ValueAdapter<?> getValueAdapter() {
		return this.valueAdapter;
	}

	public final void setDataValue(Object instance, Object value) {
		value = this.valueAdapter.convert(value);
		super.setValue(instance, value);
	}
}
