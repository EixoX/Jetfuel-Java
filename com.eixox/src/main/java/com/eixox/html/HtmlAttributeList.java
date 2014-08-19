package com.eixox.html;

import java.util.LinkedList;

public class HtmlAttributeList extends LinkedList<HtmlAttribute> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7995888532599917942L;

	public synchronized final HtmlAttributeList add(String name, String value) {
		super.add(new HtmlAttribute(name, value));
		return this;
	}

	public synchronized final String get(String name) {
		for (HtmlAttribute att : this)
			if (name.equalsIgnoreCase(att.name))
				return att.value;
		return null;
	}

	public synchronized final HtmlAttributeList set(String name, String value) {
		for (HtmlAttribute att : this)
			if (name.equalsIgnoreCase(att.name)) {
				att.value = value;
				return this;
			}
		return add(name, value);
	}

}
