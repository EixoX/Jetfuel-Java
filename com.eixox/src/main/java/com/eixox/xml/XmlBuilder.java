package com.eixox.xml;

public class XmlBuilder {

	public final StringBuilder content = new StringBuilder(101204);
	private XmlBuilderState state;

	public XmlBuilder() {
		this.state = XmlBuilderState.BLANK;
	}

	public synchronized final XmlBuilder writeXmlDeclaration(String xmlVersion) {
		content.append("<?xml version=\"");
		content.append(xmlVersion);
		content.append("\" encoding=\"UTF-8\"?>\r\n");
		this.state = XmlBuilderState.XML_DECLARATION;
		return this;
	}

	public synchronized final XmlBuilder beginElement(String name) {
		if (this.state == XmlBuilderState.OPEN_TAG)
			content.append(">\r\n");
		content.append("<");
		content.append(name);
		this.state = XmlBuilderState.OPEN_TAG;
		return this;
	}

	public synchronized final XmlBuilder closeElement(String name) {

		if (this.state == XmlBuilderState.OPEN_TAG)
			content.append(">\r\n");
		content.append("</");
		content.append(name);
		content.append(">\r\n");
		this.state = XmlBuilderState.CLOSE_TAG;
		return this;
	}

	public synchronized final XmlBuilder writeAttribute(String name, String value) {
		if (this.state != XmlBuilderState.OPEN_TAG)
			throw new RuntimeException("The writer should be in a open tag state.");
		content.append(" ");
		content.append(name);
		content.append("=\"");
		if (value != null) {
			int l = value.length();
			for (int i = 0; i < l; i++) {
				char c = value.charAt(i);
				switch (c) {
				case '\'':
					content.append("&apos;");
					break;
				case '"':
					content.append("&quot;");
					break;
				default:
					content.append(c);
				}
			}
		}
		content.append("\"");
		this.state = XmlBuilderState.OPEN_TAG;
		return this;
	}

	public synchronized final XmlBuilder writeText(String text) {
		if (this.state != XmlBuilderState.OPEN_TAG && this.state != XmlBuilderState.TEXT)
			throw new RuntimeException("The writer should be in a open tag state.");
		if (this.state == XmlBuilderState.OPEN_TAG)
			content.append(">");

		if (text != null) {
			int l = text.length();
			for (int i = 0; i < l; i++) {
				char c = text.charAt(i);

				switch (c) {
				case '<':
					content.append("&lt");
					break;
				case '>':
					content.append("&gt");
					break;
				case '&':
					content.append("&amp;");
					break;
				case '\'':
					content.append("&apos;");
					break;
				case '"':
					content.append("&quot;");
					break;
				default:
					content.append(c);
				}
			}
		}

		this.state = XmlBuilderState.TEXT;
		return this;
	}

	public final XmlBuilder writeTextElement(String name, String text) {
		return beginElement(name).writeText(text).closeElement(name);
	}

	@Override
	public final String toString() {
		return this.content.toString();
	}
}
