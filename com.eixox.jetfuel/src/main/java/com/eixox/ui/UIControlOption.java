package com.eixox.ui;

public class UIControlOption {

	private final Object key;
	private final String value;

	public UIControlOption(Object key, String value) {
		this.key = key;
		this.value = value;
	}

	public UIControlOption(Object key, Object value) {
		this(key, value == null ? "" : value.toString());
	}

	public final Object getKey() {
		return key;
	}

	public final String getValue() {
		return value;
	}

}
