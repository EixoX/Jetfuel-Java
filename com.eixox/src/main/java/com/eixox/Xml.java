package com.eixox;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class Xml {

	public static final String getAttribute(Element element, String... possibleNames) {
		for (int i = 0; i < possibleNames.length; i++)
			if (element.hasAttribute(possibleNames[i]))
				return element.getAttribute(possibleNames[i]);
		return null;
	}

	public static final Element getFirstChild(Element element, String... possibleNames) {
		final NodeList childNodes = element.getChildNodes();
		if (childNodes != null) {
			final int s = childNodes.getLength();
			for (int i = 0; i < s; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE)
					for (int j = 0; j < possibleNames.length; j++)
						if (possibleNames[j].equalsIgnoreCase(item.getNodeName()))
							return (Element) item;
			}
		}
		return null;
	}

	public static final String getFirstChildText(Element element, String... possibleNames) {
		final Element child = getFirstChild(element, possibleNames);
		return child == null ? null : child.getTextContent();
	}

	public static final ArrayList<Element> getChildNodes(Element element, String... possibleNames) {
		final ArrayList<Element> children = new ArrayList<Element>();
		final NodeList childNodes = element.getChildNodes();
		if (childNodes != null) {
			final int s = childNodes.getLength();
			for (int i = 0; i < s; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE)
					for (int j = 0; j < possibleNames.length; j++)
						if (possibleNames[j].equalsIgnoreCase(item.getNodeName()))
							children.add((Element) item);
			}
		}
		return children;
	}

	public static final String[] getChildNodeTexts(Element element, String... possibleNames) {
		final ArrayList<Element> list = getChildNodes(element, possibleNames);
		final String[] s = new String[list.size()];
		for (int i = 0; i < s.length; i++)
			s[i] = list.get(i).getTextContent();
		return s;
	}

	public static final String joinChildNodeTexts(Element element, String separator, String... possibleNames) {
		final ArrayList<Element> list = getChildNodes(element, possibleNames);
		final int s = list.size();
		if (s > 0) {
			final StringBuilder builder = new StringBuilder();
			builder.append(list.get(0).getTextContent());
			for (int i = 1; i < s; i++) {
				builder.append(separator);
				builder.append(list.get(i).getTextContent());
			}
			return builder.toString();
		} else
			return null;
	}

	@SuppressWarnings("unchecked")
	public static final <T extends XmlAdapted> T parseChildNode(Element element, Class<T> claz, String... possibleNames) {
		final Element child = getFirstChild(element, possibleNames);
		if (child == null)
			return null;
		else {
			XmlAdapted item;
			try {
				item = claz.newInstance();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			item.parse(child);
			return (T) item;
		}
	}

	@SuppressWarnings("unchecked")
	public static final <T extends XmlAdapted> ArrayList<T> parseChildNodes(Element element, Class<T> claz, String... possibleNames) {
		final ArrayList<T> list = new ArrayList<T>();
		final NodeList childNodes = element.getChildNodes();
		if (childNodes != null) {
			final int s = childNodes.getLength();
			for (int i = 0; i < s; i++) {
				Node item = childNodes.item(i);
				if (item.getNodeType() == Node.ELEMENT_NODE)
					for (int j = 0; j < possibleNames.length; j++)
						if (possibleNames[j].equalsIgnoreCase(item.getNodeName())) {
							try {
								XmlAdapted child = claz.newInstance();
								child.parse((Element) item);
								list.add((T) child);
							} catch (Exception e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}

						}
			}
		}
		return list;
	}
}
