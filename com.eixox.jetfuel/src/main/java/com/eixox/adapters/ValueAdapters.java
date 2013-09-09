package com.eixox.adapters;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;

public final class ValueAdapters {

	private static HashMap<Class<?>, ValueAdapter<?>> _adapters;

	private static synchronized final HashMap<Class<?>, ValueAdapter<?>> buildAdapters() {
		if (_adapters == null) {
			_adapters = new HashMap<Class<?>, ValueAdapter<?>>();
			_adapters.put(Boolean.TYPE, new BooleanAdapter());
			_adapters.put(Boolean.class, new BooleanAdapter());
			_adapters.put(Byte.TYPE, new ByteAdapter());
			_adapters.put(Byte.class, new ByteAdapter());
			_adapters.put(Character.TYPE, new CharAdapter());
			_adapters.put(Character.class, new CharAdapter());
			_adapters.put(Double.TYPE, new DoubleAdapter());
			_adapters.put(Double.class, new DoubleAdapter());
			_adapters.put(Float.TYPE, new FloatAdapter());
			_adapters.put(Float.class, new FloatAdapter());
			_adapters.put(Integer.TYPE, new IntAdapter());
			_adapters.put(Integer.class, new IntAdapter());
			_adapters.put(Long.TYPE, new LongAdapter());
			_adapters.put(Long.class, new LongAdapter());
			_adapters.put(String.class, new StringAdapter());
			_adapters.put(Date.class, new DateAdapter());
			_adapters.put(Calendar.class, new CalendarAdapter());
		}
		return _adapters;
	}

	public static final ValueAdapter<?> getAdapter(Class<?> claz) {

		ValueAdapter<?> adapter = buildAdapters().get(claz);
		return adapter;
	}
}
