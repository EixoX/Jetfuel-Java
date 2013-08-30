package com.eixox.xml;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AspectMember;

public class XmlAspectMemberSimple extends XmlAspectMember {

	private final ValueAdapter<?> valueAdapter;

	public XmlAspectMemberSimple(AspectMember member, String xmlName, ValueAdapter<?> valueAdapter) {
		super(member, xmlName);

		this.valueAdapter = valueAdapter;

	}

	@Override
	protected final Object parse(Element element) {

		NodeList nodes = element.getElementsByTagName(getXmlName());
		if (nodes == null || nodes.getLength() == 0)
			return null;

		String content = nodes.item(0).getTextContent();

		return valueAdapter.parse(content);
	}

}
