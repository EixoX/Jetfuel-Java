package com.eixox.html;

import java.util.LinkedList;

public class HtmlAttributeList extends LinkedList<HtmlAttribute> {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8444457371605331450L;

	public Object get(String name) {
		if (name != null && !name.isEmpty())
			for (HtmlAttribute att : this)
				if (name.equalsIgnoreCase(att.getName()))
					return att.getValue();
		return null;
	}

	public void set(String name, Object value) {
		if (name != null && !name.isEmpty()) {
			for (HtmlAttribute att : this)
				if (name.equalsIgnoreCase(att.getName()))
				{
					att.setValue(value);
					return;
				}
			super.add(new HtmlAttribute(name, value));
		}
	}
}
