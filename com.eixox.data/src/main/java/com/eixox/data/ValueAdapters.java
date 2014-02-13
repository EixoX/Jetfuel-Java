package com.eixox.data;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import com.eixox.data.adapters.BooleanAdapter;
import com.eixox.data.adapters.ByteAdapter;
import com.eixox.data.adapters.CalendarAdapter;
import com.eixox.data.adapters.CharacterAdapter;
import com.eixox.data.adapters.DateAdapter;
import com.eixox.data.adapters.DoubleAdapter;
import com.eixox.data.adapters.FloatAdapter;
import com.eixox.data.adapters.IntegerAdapter;
import com.eixox.data.adapters.LongAdapter;
import com.eixox.data.adapters.ShortAdapter;
import com.eixox.data.adapters.StringAdapter;
import com.eixox.data.adapters.UuidAdapter;

public final class ValueAdapters {

	public static final BooleanAdapter BooleanAdapter = new BooleanAdapter();
	public static final ByteAdapter ByteAdapter = new ByteAdapter();
	public static final CalendarAdapter CalendarAdapter = new CalendarAdapter();
	public static final CharacterAdapter CharacterAdapter = new CharacterAdapter();
	public static final DateAdapter DateAdapter = new DateAdapter();
	public static final DoubleAdapter DoubleAdapter = new DoubleAdapter();
	public static final FloatAdapter FloatAdapter = new FloatAdapter();
	public static final IntegerAdapter IntegerAdapter = new IntegerAdapter();
	public static final LongAdapter LongAdapter = new LongAdapter();
	public static final ShortAdapter ShortAdapter = new ShortAdapter();
	public static final StringAdapter StringAdapter = new StringAdapter();
	public static final UuidAdapter UuidAdapter = new UuidAdapter();

	private static HashMap<Class<?>, ValueAdapter<?>> adapters;

	private static synchronized HashMap<Class<?>, ValueAdapter<?>> getAdapters() {
		if (adapters == null) {
			adapters = new HashMap<Class<?>, ValueAdapter<?>>();

			adapters.put(String.class, StringAdapter);
			adapters.put(Boolean.class, BooleanAdapter);
			adapters.put(Boolean.TYPE, BooleanAdapter);
			adapters.put(Integer.class, IntegerAdapter);
			adapters.put(Integer.TYPE, IntegerAdapter);
			adapters.put(Long.class, LongAdapter);
			adapters.put(Long.TYPE, LongAdapter);
			adapters.put(Byte.class, ByteAdapter);
			adapters.put(Byte.TYPE, ByteAdapter);
			adapters.put(Short.class, ShortAdapter);
			adapters.put(Short.TYPE, ShortAdapter);
			adapters.put(Double.class, DoubleAdapter);
			adapters.put(Double.TYPE, DoubleAdapter);
			adapters.put(Float.class, FloatAdapter);
			adapters.put(Float.TYPE, FloatAdapter);
			adapters.put(Date.class, DateAdapter);
			adapters.put(java.sql.Date.class, DateAdapter);
			adapters.put(Calendar.class, CalendarAdapter);
			adapters.put(UUID.class, UuidAdapter);

		}
		return adapters;
	}

	public static final ValueAdapter<?> getAdapter(Class<?> claz) {
		return getAdapters().get(claz);
	}
}
