package com.eixox.ui;

public class Option {

	public String key;
	public String label;

	public Option() {
	}

	public Option(Object key, Object label) {
		this.key = key == null ? null : key.toString();
		this.label = label == null ? null : label.toString();
	}
}
