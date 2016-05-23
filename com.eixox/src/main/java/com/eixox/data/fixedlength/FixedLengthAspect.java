package com.eixox.data.fixedlength;

import java.lang.reflect.Field;
import java.util.HashMap;

import com.eixox.data.DataAspect;
import com.eixox.data.text.TextSchema;

public class FixedLengthAspect<T> extends DataAspect<T, FixedLengthAspectMember>
		implements TextSchema<FixedLengthAspectMember> {

	public boolean ignoreCommentLines;
	public boolean ignoreBlankLines;
	public String commentQualifier;
	public int lineOffset;

	private FixedLengthAspect(Class<T> dataType) {
		super(dataType, dataType.getSimpleName());
	}

	@Override
	protected FixedLengthAspectMember decorate(Field field) {
		FixedLength fl = field.getAnnotation(FixedLength.class);
		return fl == null ? null : new FixedLengthAspectMember(field, fl);
	}

	public T parse(String line) {
		T item = newInstance();
		for (FixedLengthAspectMember child : this)
			child.setFromLine(line, item);
		return item;
	}

	private static final HashMap<Class<?>, FixedLengthAspect<?>> INSTANCES = new HashMap<Class<?>, FixedLengthAspect<?>>();

	@SuppressWarnings("unchecked")
	public static synchronized final <T> FixedLengthAspect<T> getInstance(Class<T> claz) {
		FixedLengthAspect<?> aspect = INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new FixedLengthAspect<T>(claz);
			INSTANCES.put(claz, aspect);
		}
		return (FixedLengthAspect<T>) aspect;
	}

	public final boolean ignoreBlankLines() {
		return this.ignoreBlankLines;
	}

	public final boolean ignoreCommentLines() {
		return this.ignoreCommentLines;
	}

	public final String getCommentQualifier() {
		return this.commentQualifier;
	}

	public final Object[] parseRow(String line) {
		Object[] row = new Object[this.getColumnCount()];
		for (int i = 0; i < row.length; i++) {
			FixedLengthAspectMember member = this.get(i);
			String s = line.substring(member.start, member.end);
			Object val = member.adapter.parse(s);
			row[i] = val;
		}
		return row;
	}

	public final String formatRow(Object[] row) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < row.length; i++) {
			FixedLengthAspectMember member = get(i);
			String s = member.adapter.formatObject(row[i]);
			int l = member.end - member.start;
			if (s.length() > l)
				builder.append(s.substring(0, member.end - member.start));
			else
				for (int j = s.length(); j < l; j++)
					builder.append(' ');
		}
		return builder.toString();
	}

	public final int lineOffset() {
		return this.lineOffset;
	}
}
