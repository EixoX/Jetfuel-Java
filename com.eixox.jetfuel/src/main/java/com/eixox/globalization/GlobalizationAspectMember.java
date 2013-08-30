package com.eixox.globalization;

import org.w3c.dom.Element;

import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;

public class GlobalizationAspectMember extends DecoratedMember {

	private Element element;

	public GlobalizationAspectMember(AspectMember member) {
		super(member);
	}

	public final Element getElement() {
		return this.element;
	}

	public final void setElement(Element element) {
		this.element = element;
	}

	public final String getAttribute(String name) {
		return this.element == null ? null : this.element.getAttribute(name);
	}

}
