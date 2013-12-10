package com.eixox.html;

public class HtmlAttribute {

	private String name;
	private Object value;

	public HtmlAttribute() {
	}

	public HtmlAttribute(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public final String getName() {
		return name;
	}

	public final void setName(String name) {
		this.name = name;
	}

	public final Object getValue() {
		return value;
	}

	public final void setValue(Object value) {
		this.value = value;
	}

}
