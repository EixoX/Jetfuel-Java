package com.eixox.xml;

public class XmlWriter {

	public final StringBuilder content = new StringBuilder(101204);
	private XmlWriterState state;

	public XmlWriter() {
		this.state = XmlWriterState.BLANK;
	}

	public synchronized final XmlWriter writeXmlDeclaration(String xmlVersion) {
		content.append("<?xml version=\"");
		content.append(xmlVersion);
		content.append("\" encoding=\"UTF-8\"?>\r\n");
		this.state = XmlWriterState.XML_DECLARATION;
		return this;
	}

	public synchronized final XmlWriter beginElement(String name) {
		if (this.state == XmlWriterState.OPEN_TAG)
			content.append(">\r\n");
		content.append("<");
		content.append(name);
		this.state = XmlWriterState.OPEN_TAG;
		return this;
	}

	public synchronized final XmlWriter closeElement(String name) {

		if (this.state == XmlWriterState.OPEN_TAG)
			content.append(">\r\n");
		content.append("</");
		content.append(name);
		content.append(">\r\n");
		this.state = XmlWriterState.CLOSE_TAG;
		return this;
	}

	public synchronized final XmlWriter writeAttribute(String name, String value) {
		if (this.state != XmlWriterState.OPEN_TAG)
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
		this.state = XmlWriterState.OPEN_TAG;
		return this;
	}

	public synchronized final XmlWriter writeText(String text) {
		if (this.state != XmlWriterState.OPEN_TAG && this.state != XmlWriterState.TEXT)
			throw new RuntimeException("The writer should be in a open tag state.");
		if (this.state == XmlWriterState.OPEN_TAG)
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

		this.state = XmlWriterState.TEXT;
		return this;
	}

	public final XmlWriter writeTextElement(String name, String text) {
		return beginElement(name).writeText(text).closeElement(name);
	}

	@Override
	public final String toString() {
		return this.content.toString();
	}
}
