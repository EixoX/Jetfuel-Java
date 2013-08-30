package com.eixox.xml;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.reflection.AspectMember;

public class XmlAspectMemberCompositeList extends XmlAspectMember {

	private final XmlAspect childAspect;
	private final Class<?> childClass;

	public XmlAspectMemberCompositeList(AspectMember member, String xmlName, Class<?> childClass, XmlAspect childAspect) {
		super(member, xmlName);

		this.childClass = childClass;
		this.childAspect = childAspect;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected Object parse(Element element) {
		NodeList nodes = element.getElementsByTagName(getXmlName());
		if (nodes == null)
			return null;
		int count = nodes.getLength();
		if (count < 1)
			return null;

		ArrayList list = new ArrayList(count);

		for (int i = 0; i < count; i++) {
			Object item;
			try {
				item = childClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			this.childAspect.read(item, (Element) nodes.item(i));
			list.add(item);
		}
		return list;

	}

}
