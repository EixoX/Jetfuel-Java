package com.eixox.data.fixedlength;

import java.lang.reflect.Field;

import com.eixox.adapters.StringAdapter;
import com.eixox.adapters.ValueAdapter;
import com.eixox.data.ColumnType;
import com.eixox.data.DataAspectMember;
import com.eixox.data.text.TextColumn;

public class FixedLengthAspectMember extends DataAspectMember implements TextColumn {

	private static final ValueAdapter<?> getAdapter(FixedLength annotation) {
		try {
			return annotation.adapter().equals(StringAdapter.class) ? null : annotation.adapter().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public FixedLengthAspectMember(Field field, FixedLength annotation) {
		super(field,
				ColumnType.NORMAL,
				true,
				annotation.name(),
				getAdapter(annotation));

		this.start = annotation.start();
		this.end = annotation.end();
	}

	public int start;
	public int end;

	public final void setFromLine(String source, Object target) {
		this.setFromString(source.substring(start, end), target);
	}

	public final ValueAdapter<?> getAdapter() {
		return this.adapter;
	}

}
