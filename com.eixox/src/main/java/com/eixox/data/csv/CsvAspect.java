package com.eixox.data.csv;

import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class CsvAspect<T> extends AbstractAspect<CsvAspectMember> {

	private CsvAspect(Class<T> dataType) {
		super(dataType);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected CsvAspectMember decorate(AspectMember member) {
		CsvColumn annotation = member.getAnnotation(CsvColumn.class);
		if (annotation != null)
			return new CsvAspectMember(member, annotation);
		else
			return null;
	}

	private static final HashMap<Class<?>, CsvAspect<?>> INSTANCES = new HashMap<Class<?>, CsvAspect<?>>();

	public static synchronized final <T> CsvAspect<T> getInstance(Class<T> claz) {
		@SuppressWarnings("unchecked")
		CsvAspect<T> aspect = (CsvAspect<T>) INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new CsvAspect<T>(claz);
			INSTANCES.put(claz, aspect);
		}
		return aspect;
	}

}
