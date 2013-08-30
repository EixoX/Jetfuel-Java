package com.eixox.xml;

import org.w3c.dom.Element;

import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AspectMember;

public class XmlAspectMemberAttribute extends XmlAspectMember {

	private final ValueAdapter<?> valueAdapter;

	public XmlAspectMemberAttribute(AspectMember member, String xmlName, ValueAdapter<?> valueAdapter) {
		super(member, xmlName);
		this.valueAdapter = valueAdapter;
	}

	@Override
	protected Object parse(Element element) {
		String attContent = element.getAttribute(getXmlName());
		return valueAdapter.parse(attContent);
	}

}
