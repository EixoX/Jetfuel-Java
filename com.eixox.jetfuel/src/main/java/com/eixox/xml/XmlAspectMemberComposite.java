package com.eixox.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.reflection.AspectMember;

public class XmlAspectMemberComposite extends XmlAspectMember {
	private final XmlAspect childAspect;
	private final Class<?> childClass;

	public XmlAspectMemberComposite(AspectMember member, String xmlName, Class<?> childClass, XmlAspect childAspect) {
		super(member, xmlName);

		this.childClass = childClass;
		this.childAspect = childAspect;

	}

	@Override
	protected final Object parse(Element element) {

		NodeList nodes = element.getElementsByTagName(getXmlName());
		if (nodes == null || nodes.getLength() == 0)
			return null;

		Object item;
		try {
			item = this.childClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}

		this.childAspect.read(item, (Element) nodes.item(0));

		return item;

	}
}
