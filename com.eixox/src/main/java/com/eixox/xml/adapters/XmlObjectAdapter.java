package com.eixox.xml.adapters;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.Strings;
import com.eixox.xml.XmlAspect;
import com.eixox.xml.XmlAspectMember;

public class XmlObjectAdapter implements XmlAdapter {

	public final XmlAspect aspect;
	public final String xmlName;

	public XmlObjectAdapter(String xmlName, Class<?> dataType) {
		this.aspect = XmlAspect.getInstance(dataType);
		this.xmlName = Strings.isNullOrEmptyAlternate(xmlName, this.aspect.xmlName);
	}

	public XmlObjectAdapter(Class<?> dataType) {
		this(null, dataType);
	}

	public Object readXml(Element parent) {
		NodeList nodes = parent.getElementsByTagName(this.xmlName);
		if (nodes.getLength() < 1)
			return null;
		else {
			Element element = (Element) nodes.item(0);
			Object instance = aspect.newInstance();
			for (XmlAspectMember member : aspect) {
				member.readXml(instance, element);
			}
			return instance;
		}
	}

	public void writeXml(Element parent, Object value) {
		Element child = parent.getOwnerDocument().createElement(this.xmlName);
		for (XmlAspectMember member : aspect) {
			member.writeXml(child, member.getValue(value));
		}
		parent.appendChild(child);
	}

}
