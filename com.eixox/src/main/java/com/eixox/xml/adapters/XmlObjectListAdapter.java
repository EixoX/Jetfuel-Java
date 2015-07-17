package com.eixox.xml.adapters;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.Strings;
import com.eixox.xml.XmlAspect;
import com.eixox.xml.XmlAspectMember;

public class XmlObjectListAdapter implements XmlAdapter {

	public final String xmlName;
	public final XmlAspect xmlAspect;

	public XmlObjectListAdapter(String xmlName, Class<?> componentType) {
		this.xmlAspect = XmlAspect.getInstance(componentType);
		this.xmlName = Strings.isNullOrEmptyAlternate(xmlName, xmlAspect.xmlName);
	}

	@SuppressWarnings("rawtypes")
	public Object readXml(Element parent) {
		NodeList nodes = parent.getElementsByTagName(xmlName);
		int length = nodes.getLength();
		ArrayList list = new ArrayList(length);
		for (int i = 0; i < length; i++) {
			Object instance = xmlAspect.newInstance();
			for (XmlAspectMember member : this.xmlAspect) {
				member.readXml(instance, (Element) nodes.item(i));
			}
		}
		return list;
	}

	public void writeXml(Element parent, Object value) {
		throw new RuntimeException("NOT IMPLEMENTED YET");
	}

}
