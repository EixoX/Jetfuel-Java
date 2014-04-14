package com.eixox.html;

public final class HtmlAttribute {

	private final String name;
	private final Object value;

	public HtmlAttribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public final String getName() {
		return name;
	}

	public final Object getValue() {
		return value;
	}

}
