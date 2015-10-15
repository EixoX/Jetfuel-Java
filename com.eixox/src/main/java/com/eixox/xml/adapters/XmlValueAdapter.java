package com.eixox.xml.adapters;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.eixox.adapters.ValueAdapter;
import com.eixox.xml.XmlType;

public class XmlValueAdapter implements XmlAdapter {

	public final String xmlName;
	public final XmlType xmlValueFormat;
	public final ValueAdapter<?> valueAdapter;

	public XmlValueAdapter(String xmlName, XmlType xmlValueFormat, ValueAdapter<?> valueAdapter) {
		this.xmlName = xmlName;
		this.xmlValueFormat = xmlValueFormat;
		this.valueAdapter = valueAdapter;
	}

	public Object readXml(Element parent) {
		String content = null;
		switch (this.xmlValueFormat) {
		case ATTRIBUTE:
			content = parent.getAttribute(this.xmlName);
			break;
		default:
			NodeList nodeList = parent.getElementsByTagName(this.xmlName);
			content = nodeList.getLength() > 0 ?
					nodeList.item(0).getTextContent() :
					null;
			break;
		}
		return this.valueAdapter.parseObject(content);
	}

	public void writeXml(Element parent, Object value) {
		switch (this.xmlValueFormat) {
		case ATTRIBUTE:
			parent.setAttribute(xmlName, valueAdapter.formatObject(value));
			break;
		case CDATA:
			Element el1 = parent.getOwnerDocument().createElement(this.xmlName);
			Node cdata = parent.getOwnerDocument().createCDATASection(valueAdapter.formatObject(value));
			el1.appendChild(cdata);
			parent.appendChild(el1);
			break;
		case TEXT:
			Element el2 = parent.getOwnerDocument().createElement(this.xmlName);
			Text txtNode = el2.getOwnerDocument().createTextNode(valueAdapter.formatObject(value));
			el2.appendChild(txtNode);
			parent.appendChild(el2);
			break;
		}
	}
}
