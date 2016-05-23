package com.eixox.xml.adapters;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.adapters.ValueAdapter;
import com.eixox.xml.XmlType;

public class XmlValueListAdapter implements XmlAdapter {

	public final String xmlName;
	public final XmlType xmlValueFormat;
	public final ValueAdapter<?> valueAdapter;

	public XmlValueListAdapter(String xmlName, XmlType xmlValueFormat, ValueAdapter<?> valueAdapter) {
		this.xmlName = xmlName;
		this.xmlValueFormat = xmlValueFormat;
		this.valueAdapter = valueAdapter;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object readXml(Element parent) {
		ArrayList value = new ArrayList();
		if (this.xmlValueFormat == XmlType.ATTRIBUTE) {
			String content = parent.getAttribute(xmlName);
			if (content != null && content.length() > 0) {
				String[] valueStrings = content.split(";");
				for (int i = 0; i < valueStrings.length; i++) {
					Object arrayValue1 = valueAdapter.parse(valueStrings[i]);
					value.add(arrayValue1);
				}
			}
		}
		else {
			NodeList nodeList = parent.getElementsByTagName(this.xmlName);
			int length = nodeList.getLength();
			if (length > 0) {
				for (int i = 0; i < length; i++)
				{
					String elContent = nodeList.item(i).getTextContent();
					Object arrayValue2 = valueAdapter.parse(elContent);
					value.add(arrayValue2);
				}
			}
		}
		return value;
	}

	public void writeXml(Element parent, Object value) {
		throw new RuntimeException("NOT IMPLEMENTED");

	}

}
