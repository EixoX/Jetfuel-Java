package com.eixox.xml;

import java.lang.reflect.Array;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AspectMember;

public class XmlAspectMemberSimpleArray extends XmlAspectMember {

	private final ValueAdapter<?> valueAdapter;
	private final Class<?> childClass;

	public XmlAspectMemberSimpleArray(AspectMember member, String xmlName, Class<?> childClass, ValueAdapter<?> valueAdapter) {
		super(member, xmlName);

		this.childClass = childClass;
		this.valueAdapter = valueAdapter;
	}

	@Override
	protected Object parse(Element element) {
		NodeList nodes = element.getElementsByTagName(getXmlName());
		if (nodes == null)
			return null;
		int count = nodes.getLength();
		if (count < 1)
			return null;

		Object arr = Array.newInstance(childClass, count);

		for (int i = 0; i < count; i++) {
			String text = nodes.item(i).getTextContent();
			Object item = valueAdapter.parse(text);
			Array.set(arr, i, item);
		}
		return arr;

	}

}
