package com.eixox.html;

import com.eixox.StringHelper;

public class HtmlBuilder {

	private final StringBuilder builder;

	public HtmlBuilder() {
		this.builder = new StringBuilder();
	}

	public HtmlBuilder(int capacity) {
		this.builder = new StringBuilder(capacity);
	}

	public final void writeRaw(String raw) {
		builder.append(raw);
	}

	public final void writeText(String text) {
		if (text != null)
			builder.append(StringHelper.htmlEncode(text));
	}

	public final void writeAttribute(String name, Object value) {
		if (name != null && !name.isEmpty()) {
			builder.append(' ');
			builder.append(name);
			builder.append("=\"");
			builder.append(StringHelper.attributeEncode(value));
			builder.append("\"");
		}
	}

	public final void writeAttribute(HtmlAttribute attribute) {
		writeAttribute(attribute.getName(), attribute.getValue());
	}

	public final void writeElement(String tagName, boolean isEmpty) {
		builder.append("<");
		builder.append(tagName);
		builder.append(isEmpty ? " />" : ">");
	}

	public final void writeElement(String tagName, boolean isEmpty, HtmlAttribute... attributes) {
		builder.append("<");
		builder.append(tagName);
		for (HtmlAttribute a : attributes)
			writeAttribute(a.getName(), a.getValue());
		builder.append(isEmpty ? " />" : ">");
	}

	public final void closeElement(String tagName) {
		builder.append("</");
		builder.append(tagName);
		builder.append(">\r\n");
	}

	public final void writeSimpleElement(String tagName, Object value) {
		builder.append("<");
		builder.append(tagName);
		builder.append(">");
		if (value != null)
			builder.append(StringHelper.htmlEncode(value.toString()));
		builder.append("</");
		builder.append(tagName);
		builder.append(">\r\n");
	}

	public final void writeSimpleElement(String tagName, Object value, HtmlAttribute... attributes) {
		builder.append("<");
		builder.append(tagName);
		for (HtmlAttribute a : attributes)
			writeAttribute(a.getName(), a.getValue());
		builder.append(">");
		if (value != null)
			builder.append(StringHelper.htmlEncode(value.toString()));
		builder.append("</");
		builder.append(tagName);
		builder.append(">\r\n");
	}

	public final void beginElement(String tagName, String cssClass) {
		builder.append("<");
		builder.append(tagName);
		writeAttribute("class", cssClass);
		builder.append(">\r\n");
	}

	public final void beginElement(String tagName, String cssClass, String cssStyle) {
		builder.append("<");
		builder.append(tagName);
		if (cssClass != null)
			writeAttribute("class", cssClass);
		if (cssStyle != null)
			writeAttribute("style", cssStyle);
		builder.append(">\r\n");
	}

	public final void beginElement(String tagName, String id, String cssClass, String cssStyle) {
		builder.append("<");
		builder.append(tagName);
		writeAttribute("id", id);
		if (cssClass != null)
			writeAttribute("class", cssClass);
		if (cssStyle != null)
			writeAttribute("style", cssStyle);
		builder.append(">\r\n");
	}

	public final void beginElement(String tagName, HtmlAttribute... attributes) {
		builder.append("<");
		builder.append(tagName);
		for (HtmlAttribute a : attributes)
			writeAttribute(a.getName(), a.getValue());
		builder.append(">");
	}

	@Override
	public String toString() {
		return this.builder.toString();
	}
}
