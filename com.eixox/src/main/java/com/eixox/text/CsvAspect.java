package com.eixox.text;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.eixox.data.DataAspect;
import com.eixox.globalization.Culture;
import com.eixox.globalization.Cultures;
import com.eixox.reflection.AspectMember;

public class CsvAspect<T> extends DataAspect<CsvAspectMember> {

	private String separator;
	private static final HashMap<Class<?>, CsvAspect<?>> instances = new HashMap<Class<?>, CsvAspect<?>>();
	private Culture culture;

	@SuppressWarnings("unchecked")
	public synchronized static final <E> CsvAspect<E> getAspect(Class<E> claz) {
		CsvAspect<E> aspect = (CsvAspect<E>) instances.get(claz);
		if (aspect == null) {
			aspect = new CsvAspect<E>(claz);
			instances.put(claz, aspect);
		}
		return aspect;
	}

	private final static String getTableName(Class<?> dataType) {
		CsvTable tb = dataType.getAnnotation(CsvTable.class);
		if (tb == null)
			throw new RuntimeException("Please annotate " + dataType + " as @Table");
		else
			return tb.dataName();
	}

	private CsvAspect(Class<?> dataType) {
		super(dataType, getTableName(dataType));

		CsvTable ct = dataType.getAnnotation(CsvTable.class);

		this.separator = ct.separator();
		this.culture = Cultures.getCulture(ct.culture());
	}

	public final String getSeparator() {
		return separator;
	}

	public final void setSeparator(String separator) {
		this.separator = separator;
	}

	public final Culture getCulture() {
		return culture;
	}

	public final void setCulture(Culture culture) {
		this.culture = culture;
	}

	@Override
	protected final CsvAspectMember decorate(AspectMember member) {
		CsvColumn column = member.getAnnotation(CsvColumn.class);
		if (column == null)
			return null;
		else
			return new CsvAspectMember(member, column);
	}

	public final ArrayList<T> readAll(BufferedReader bufferedReader) {
		final ArrayList<T> list = new ArrayList<T>();
		final CsvAspectReader<T> reader = new CsvAspectReader<T>(this, bufferedReader);
		while (reader.hasNext())
			list.add(reader.next());
		return list;
	}

}
