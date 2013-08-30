package com.eixox.xml;

import org.w3c.dom.Element;

import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;

public abstract class XmlAspectMember extends DecoratedMember {

	private final String xmlName;

	public XmlAspectMember(AspectMember member, String xmlName) {
		super(member);
		this.xmlName = xmlName == null || xmlName.isEmpty() ? member.getName() : xmlName;

	}

	public final String getXmlName() {
		return this.xmlName;
	}

	protected abstract Object parse(Element element);

	public final void read(Object entity, Element element) {
		Object value = parse(element);
		if (value != null)
			super.setValue(entity, value);
	}
}
