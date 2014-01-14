package com.eixox.html;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringEscapeUtils;

import com.eixox.StringHelper;

public class HtmlWriter {

	private final Writer writer;

	public HtmlWriter(Writer writer) {
		this.writer = writer;
	}

	public final void writeRaw(String raw) throws IOException {
		writer.write(raw);
	}

	public final void writeText(String text) throws IOException {
		if (text != null)
			writer.write(StringEscapeUtils.escapeHtml(text));
	}

	public final void writeAttribute(String name, Object value) throws IOException {
		if (name != null && !name.isEmpty()) {
			writer.write(' ');
			writer.write(name);
			writer.write("=\"");
			writer.write(StringHelper.attributeEncode(value));
			writer.write("\"");
		}
	}

	public final void writeAttribute(HtmlAttribute attribute) throws IOException {
		writeAttribute(attribute.getName(), attribute.getValue());
	}

	public final void writeElement(String tagName, boolean isEmpty) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writer.write(isEmpty ? " />" : ">");
	}

	public final void writeElement(String tagName, boolean isEmpty, HtmlAttribute... attributes) throws IOException {
		writer.write("<");
		writer.write(tagName);
		for (HtmlAttribute a : attributes)
			writeAttribute(a.getName(), a.getValue());
		writer.write(isEmpty ? " />" : ">");
	}

	public final void closeElement(String tagName) throws IOException {
		writer.write("</");
		writer.write(tagName);
		writer.write(">\r\n");
	}

	public final void writeSimpleElement(String tagName, Object value) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writer.write(">");
		if (value != null)
			writer.write(StringEscapeUtils.escapeHtml(value.toString()));
		writer.write("</");
		writer.write(tagName);
		writer.write(">\r\n");
	}

	public final void writeSimpleElement(String tagName, Object value, HtmlAttribute... attributes) throws IOException {
		writer.write("<");
		writer.write(tagName);
		for (HtmlAttribute a : attributes)
			writeAttribute(a.getName(), a.getValue());
		writer.write(">");
		if (value != null)
			writer.write(StringEscapeUtils.escapeHtml(value.toString()));
		writer.write("</");
		writer.write(tagName);
		writer.write(">\r\n");
	}

	public final void beginElement(String tagName, String cssClass) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writeAttribute("class", cssClass);
		writer.write(">\r\n");
	}

	public final void beginElement(String tagName, String cssClass, String cssStyle) throws IOException {
		writer.write("<");
		writer.write(tagName);
		if (cssClass != null)
			writeAttribute("class", cssClass);
		if (cssStyle != null)
			writeAttribute("style", cssStyle);
		writer.write(">\r\n");
	}

	public final void beginElement(String tagName, String id, String cssClass, String cssStyle) throws IOException {
		writer.write("<");
		writer.write(tagName);
		writeAttribute("id", id);
		if (cssClass != null)
			writeAttribute("class", cssClass);
		if (cssStyle != null)
			writeAttribute("style", cssStyle);
		writer.write(">\r\n");
	}
}
