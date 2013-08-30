package com.eixox.xml;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AspectMember;

public class XmlAspectMemberSimpleList extends XmlAspectMember {

	private final ValueAdapter<?> valueAdapter;

	public XmlAspectMemberSimpleList(AspectMember member, String xmlName, ValueAdapter<?> valueAdapter) {
		super(member, xmlName);

		this.valueAdapter = valueAdapter;
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

		ArrayList arr = new ArrayList(count);

		for (int i = 0; i < count; i++) {
			String text = nodes.item(i).getTextContent();
			Object item = valueAdapter.parse(text);
			arr.add(item);
		}
		return arr;

	}

}
