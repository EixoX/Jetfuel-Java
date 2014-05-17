package com.eixox.ui;

public class UIControlOption {

	private final Object key;
	private final String name;

	public UIControlOption(Object key, String name) {
		this.key = key;
		this.name = name;
	}

	public UIControlOption(Object key, Object name) {
		this(key, name == null ? key.toString() : name.toString());
	}

	public final Object getKey() {
		return key;
	}

	public final String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.key == null ? this.name : this.key.toString() + "=" + this.name;
	}

}
