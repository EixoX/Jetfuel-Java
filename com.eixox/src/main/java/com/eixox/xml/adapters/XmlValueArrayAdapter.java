package com.eixox.xml.adapters;

import java.lang.reflect.Array;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.adapters.ValueAdapter;
import com.eixox.xml.XmlValueFormat;

public class XmlValueArrayAdapter implements XmlAdapter {

	public final String xmlName;
	public final XmlValueFormat xmlValueFormat;
	public final ValueAdapter<?> valueAdapter;

	public XmlValueArrayAdapter(String xmlName, XmlValueFormat xmlValueFormat, ValueAdapter<?> valueAdapter) {
		this.xmlName = xmlName;
		this.xmlValueFormat = xmlValueFormat;
		this.valueAdapter = valueAdapter;
	}

	public Object readXml(Element parent) {
		Object value = null;
		if (this.xmlValueFormat == XmlValueFormat.ATTRIBUTE) {
			String content = parent.getAttribute(xmlName);
			if (content != null && content.length() > 0) {
				String[] valueStrings = content.split(";");
				value = Array.newInstance(valueAdapter.getDataType(), valueStrings.length);
				for (int i = 0; i < valueStrings.length; i++) {
					Object arrayValue1 = valueAdapter.parseObject(valueStrings[i]);
					Array.set(value, i, arrayValue1);
				}
			}
		}
		else {
			NodeList nodeList = parent.getElementsByTagName(this.xmlName);
			int length = nodeList.getLength();
			if (length > 0) {
				value = Array.newInstance(valueAdapter.getDataType(), length);
				for (int i = 0; i < length; i++)
				{
					String elContent = nodeList.item(i).getTextContent();
					Object arrayValue2 = valueAdapter.parseObject(elContent);
					Array.set(value, i, arrayValue2);
				}
			}
		}
		return value;
	}

	public void writeXml(Element parent, Object value) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}

}
