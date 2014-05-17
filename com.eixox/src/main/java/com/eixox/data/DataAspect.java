package com.eixox.data;

import com.eixox.Strings;
import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AbstractAspect;

public abstract class DataAspect<T extends DataAspectMember> extends AbstractAspect<T> {

	private final String	dataName;

	protected DataAspect(Class<?> dataType, String dataName) {
		super(dataType);
		this.dataName = Strings.isNullOrEmptyAlternate(dataName, dataType.getSimpleName());
	}

	public final int getDataOrdinal(String name) {
		int s = super.getCount();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(super.get(i).getDataName()))
				return i;
		return -1;
	}

	public final int getDataOrdinalOrException(String name) {
		int ordinal = getDataOrdinal(name);
		if (ordinal < 0)
			throw new RuntimeException("No column data name mapped to " + name + " on " + super.getDataType());
		else
			return ordinal;
	}

	public final String getColumnName(int ordinal) {
		return super.get(ordinal).getDataName();
	}

	public final String getDataName() {
		return this.dataName;
	}

	public final <G> void setDataValue(Object entity, int ordinal, G value) {
		final DataAspectMember dataAspectMember = super.get(ordinal);
		final ValueAdapter<?> valueAdapter = dataAspectMember.getValueAdapter();
		final Object newValue = valueAdapter.convert(value);
		dataAspectMember.setValue(entity, newValue);
	}
}
