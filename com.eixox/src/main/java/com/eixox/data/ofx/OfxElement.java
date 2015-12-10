package com.eixox.data.ofx;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.eixox.Strings;
import com.eixox.adapters.DateYmdAdapter;

public class OfxElement {

	public String name;

	public String text;

	public LinkedList<OfxElement> children;

	public final void appendElementsByName(List<OfxElement> target, String name) {
		if (name.equalsIgnoreCase(this.name))
			target.add(this);
		if (this.children != null)
			for (OfxElement child : this.children)
				child.appendElementsByName(target, name);
	}

	public final String getText(String name) {
		if (name.equalsIgnoreCase(this.name))
			return this.text;
		else {
			for (OfxElement child : this.children) {
				if (name.equalsIgnoreCase(child.name))
					return child.text;
			}
		}
		return null;
	}

	public final long getLong(String name) {
		String txt = getText(name);
		return txt == null || txt.isEmpty() ? 0L : Long.parseLong(txt);
	}

	public final double getDouble(String name) {
		String txt = getText(name);
		return txt == null || txt.isEmpty() ? 0D : Double.parseDouble(txt);
	}

	public final Date getDate(String name) {
		String txt = getText(name);
		return txt == null || txt.isEmpty() ? null : DateYmdAdapter.INSTANCE.parse(Strings.left(txt, 8));
	}
}
