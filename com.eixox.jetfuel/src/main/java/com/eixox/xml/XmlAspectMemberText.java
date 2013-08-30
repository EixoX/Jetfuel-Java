package com.eixox.xml;

import org.w3c.dom.Element;

import com.eixox.adapters.ValueAdapter;
import com.eixox.reflection.AspectMember;

public class XmlAspectMemberText extends XmlAspectMember {
	private final ValueAdapter<?> valueAdapter;

	public XmlAspectMemberText(AspectMember member, String xmlName, ValueAdapter<?> valueAdapter) {
		super(member, xmlName);
		this.valueAdapter = valueAdapter;
	}

	@Override
	protected Object parse(Element element) {
		String attContent = element.getTextContent();
		return valueAdapter.parse(attContent);
	}
}
