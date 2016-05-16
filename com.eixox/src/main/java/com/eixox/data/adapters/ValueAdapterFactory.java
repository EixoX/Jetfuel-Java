package com.eixox.data.adapters;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.HashMap;

public final class ValueAdapterFactory {

	private static final HashMap<Class<?>, ValueAdapter<?>> ADAPTERS;
	static {
		ADAPTERS = new HashMap<Class<?>, ValueAdapter<?>>();
		ADAPTERS.put(Boolean.TYPE, new BooleanAdapter());
		ADAPTERS.put(Byte.TYPE, new ByteAdapter());
		ADAPTERS.put(Character.TYPE, new CharAdapter());
		ADAPTERS.put(Date.class, new DateAdapter());
		ADAPTERS.put(Double.TYPE, new DoubleAdapter());
		ADAPTERS.put(Float.TYPE, new FloatAdapter());
		ADAPTERS.put(Integer.TYPE, new IntegerAdapter());
		ADAPTERS.put(Long.TYPE, new LongAdapter());
		ADAPTERS.put(Number.class, new NumberAdapter());
		ADAPTERS.put(Short.TYPE, new ShortAdapter());
		ADAPTERS.put(String.class, new StringAdapter());
		ADAPTERS.put(Time.class, new TimeAdapter());
		ADAPTERS.put(Timestamp.class, new TimestampAdapter());
	}

	public static final ValueAdapter<?> getAdapter(Class<?> claz) {
		return ADAPTERS.get(claz);
	}

}
