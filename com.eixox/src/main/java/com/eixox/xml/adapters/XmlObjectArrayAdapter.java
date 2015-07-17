package com.eixox.xml.adapters;

import java.lang.reflect.Array;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.Strings;
import com.eixox.xml.XmlAspect;
import com.eixox.xml.XmlAspectMember;

public class XmlObjectArrayAdapter implements XmlAdapter {

	public final String xmlName;
	public final XmlAspect xmlAspect;

	public XmlObjectArrayAdapter(String xmlName, Class<?> componentType) {
		this.xmlAspect = XmlAspect.getInstance(componentType);
		this.xmlName = Strings.isNullOrEmptyAlternate(xmlName, xmlAspect.xmlName);
	}

	public Object readXml(Element parent) {

		NodeList nodes = parent.getElementsByTagName(xmlName);
		int length = nodes.getLength();
		Object array = Array.newInstance(xmlAspect.getDataType(), length);
		for (int i = 0; i < length; i++) {
			Object instance = xmlAspect.newInstance();
			for (XmlAspectMember member : xmlAspect)
				member.readXml(instance, (Element) nodes.item(i));
		}
		return array;

	}

	public void writeXml(Element parent, Object value) {
		throw new RuntimeException("NOT IMPLEMENTED");

	}

}
