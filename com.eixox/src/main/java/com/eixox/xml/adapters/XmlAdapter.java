package com.eixox.xml.adapters;

import org.w3c.dom.Element;

public interface XmlAdapter {

	public Object readXml(Element parent);

	public void writeXml(Element parent, Object value);

}
