package com.eixox.data.fixedlength;

import java.lang.reflect.Field;

import com.eixox.data.ColumnType;
import com.eixox.data.adapters.StringAdapter;
import com.eixox.data.adapters.ValueAdapter;
import com.eixox.data.text.TextAspectMember;

public class FixedLengthAspectMember extends TextAspectMember {

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
				null,
				true,
				getAdapter(annotation));
		this.start = annotation.start();
		this.end = annotation.end();

	}

	public int start;
	public int end;

}
