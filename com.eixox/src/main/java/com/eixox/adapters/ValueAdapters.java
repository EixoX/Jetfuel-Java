package com.eixox.adapters;

import java.util.HashMap;

public final class ValueAdapters {

	public static final BooleanAdapter						BOOLEAN		= new BooleanAdapter();
	public static final ByteAdapter							BYTE		= new ByteAdapter();
	public static final CalendarAdapter						CALENDAR	= new CalendarAdapter();
	public static final CharacterAdapter					CHARACTER	= new CharacterAdapter();
	public static final DateTimeAdapter						DATE_TIME	= new DateTimeAdapter();
	public static final DoubleAdapter						DOUBLE		= new DoubleAdapter();
	public static final FloatAdapter						FLOAT		= new FloatAdapter();
	public static final IntegerAdapter						INTEGER		= new IntegerAdapter();
	public static final LongAdapter							LONG		= new LongAdapter();
	public static final NumberAdapter						NUMBER		= new NumberAdapter();
	public static final ShortAdapter						SHORT		= new ShortAdapter();
	public static final StringAdapter						STRING		= new StringAdapter();
	public static final UUIDAdapter							UUID		= new UUIDAdapter();

	private static final HashMap<Class<?>, ValueAdapter<?>>	adapters;
	static {
		adapters = new HashMap<Class<?>, ValueAdapter<?>>();

		adapters.put(Boolean.TYPE, BOOLEAN);
		adapters.put(Byte.TYPE, BYTE);
		adapters.put(Character.TYPE, CHARACTER);
		adapters.put(Double.TYPE, DOUBLE);
		adapters.put(Float.TYPE, FLOAT);
		adapters.put(Integer.TYPE, INTEGER);
		adapters.put(Long.TYPE, LONG);
		adapters.put(Short.TYPE, SHORT);

		adapters.put(BOOLEAN.getDataType(), BOOLEAN);
		adapters.put(BYTE.getDataType(), BYTE);
		adapters.put(CALENDAR.getDataType(), CALENDAR);
		adapters.put(CHARACTER.getDataType(), CHARACTER);
		adapters.put(DATE_TIME.getDataType(), DATE_TIME);
		adapters.put(DOUBLE.getDataType(), DOUBLE);
		adapters.put(FLOAT.getDataType(), FLOAT);
		adapters.put(INTEGER.getDataType(), INTEGER);
		adapters.put(LONG.getDataType(), LONG);
		adapters.put(NUMBER.getDataType(), NUMBER);
		adapters.put(SHORT.getDataType(), SHORT);
		adapters.put(STRING.getDataType(), STRING);
		adapters.put(UUID.getDataType(), UUID);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static synchronized final ValueAdapter<?> getAdapter(Class<?> claz) {
		ValueAdapter<?> valueAdapter = adapters.get(claz);
		if (valueAdapter == null && Enum.class.isAssignableFrom(claz)) {
			valueAdapter = new EnumAdapter((Class<? extends Enum>) claz);
			adapters.put(valueAdapter.getDataType(), valueAdapter);
		}
		return valueAdapter;
	}

	public static final void registerAdapter(ValueAdapter<?> adapter) {
		adapters.put(adapter.getDataType(), adapter);
	}
}
