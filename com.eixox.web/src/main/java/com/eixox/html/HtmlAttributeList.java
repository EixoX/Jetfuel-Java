package com.eixox.html;

import java.util.LinkedList;

public class HtmlAttributeList extends LinkedList<HtmlAttribute> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8444457371605331450L;

	public final HtmlAttribute add(String name, Object value) {
		HtmlAttribute att = new HtmlAttribute(name, value);
		super.add(att);
		return att;
	}

	public final Object get(String name) {
		for (HtmlAttribute att : this)
			if (name.equalsIgnoreCase(att.getName()))
				return att.getValue();

		return null;
	}

	public final Object set(String name, Object value) {
		for (HtmlAttribute att : this)
			if (name.equalsIgnoreCase(att.getName())) {
				Object old = att.getValue();
				att.setValue(value);
				return old;
			}

		super.add(new HtmlAttribute(name, value));
		return null;
	}
}
