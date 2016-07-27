package com.eixox.web;

import com.eixox.ui.UIPresentationMember;

public class SemanticUIPresenter {

	private final void appendHtml(StringBuilder builder, String in) {
		if (in != null) {
			int s = in.length();
			for (int i = 0; i < s; i++) {
				char c = in.charAt(i);
				switch (c) {
				case 34:
					builder.append("&quot;");
					break;
				case 38:
					builder.append("&amp;");
					break;
				case 60:
					builder.append("&lt;");
					break;
				case 62:
					builder.append("&gt;");
					break;
				default:
					builder.append(c);
					break;
				}
			}
		}
	}

	private final void renderLabel(StringBuilder builder, UIPresentationMember item) {
		builder.append("<label>");
		appendHtml(builder, item.label);
		builder.append("</label>\r\n");
	}

	private final void openWrapper(StringBuilder builder, UIPresentationMember item, String baseClass) {
		builder.append("<div id=\"wr_");
		builder.append(item.name);
		builder.append("\" class=\"");
		builder.append(baseClass);
		switch (item.controlState) {
		case ERROR:
			builder.append(" error");
			break;
		case SUCCESS:
			builder.append(" positive");
			break;
		case WARNING:
			builder.append(" warning");
			break;
		default:
			break;
		}
		if (item.appendCss != null && !item.appendCss.isEmpty()) {
			builder.append(item.appendCss);
		}
		builder.append("\">\r\n");
	}

	public final void closeWrapper(StringBuilder builder, UIPresentationMember item) {
		if (item.hint != null && !item.hint.isEmpty()) {
			builder.append("<p class=\"hint\">");
			appendHtml(builder, item.hint);
			builder.append("</p>\r\n");
		}
		builder.append("</div>\r\n");
	}

	public final String render(UIPresentationMember item) {

		StringBuilder builder = new StringBuilder(1024);

		switch (item.controlType) {
		case CHECKBOX:
			throw new RuntimeException("Not implemented");

		case CHECKBOX_GROUP:
			throw new RuntimeException("Not implemented");

		case DATE:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "date");
			closeWrapper(builder, item);
			break;
		case DATETIME:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "datetime");
			closeWrapper(builder, item);
			break;
		case DROPDOWN:
			throw new RuntimeException("Not implemented");
		case EMAIL:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "email");
			closeWrapper(builder, item);
			break;
		case FILE:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "file");
			closeWrapper(builder, item);
			break;
		case HIDDEN:
			renderTextInput(builder, item, "hidden");
			break;
		case HTML:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextaea(builder, item, "html-editor");
			closeWrapper(builder, item);
			break;
		case MULTILINE:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextaea(builder, item, "");
			closeWrapper(builder, item);
			break;
		case MULTISELECT:
			throw new RuntimeException("Not implemented");
		case NONE:
			break;
		case NUMERIC:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "number");
			closeWrapper(builder, item);
			break;
		case PASSWORD:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "password");
			closeWrapper(builder, item);
			break;
		case RADIO_GROUP:
			throw new RuntimeException("Not implemented");
		case SINGLE_LINE:
			openWrapper(builder, item, "field");
			renderLabel(builder, item);
			renderTextInput(builder, item, "text");
			closeWrapper(builder, item);
			break;
		default:
			throw new RuntimeException("Not implemented");

		}

		return builder.toString();
	}

	private final void renderTextInput(StringBuilder builder, UIPresentationMember item, String inputType) {
		builder.append("<input type=\"");
		builder.append(inputType);
		builder.append("\" name=\"");
		builder.append(item.name);
		builder.append("\" id=\"");
		builder.append(item.id);
		builder.append("\"");
		if (item.required) {
			builder.append(" required=\"required\"");
		}
		if (item.maxlength > 0) {
			builder.append(" maxlength=\"");
			builder.append(item.maxlength);
			builder.append("\"");
		}
		builder.append(" value=\"");
		this.appendHtml(builder, item.value);
		builder.append("\">\r\n");

	}

	private final void renderTextaea(StringBuilder builder, UIPresentationMember item, String textAreaClass) {
		builder.append("<input type=\"textarea\" class=\"");
		builder.append(textAreaClass);
		builder.append("\" name=\"");
		builder.append(item.name);
		builder.append("\" id=\"");
		builder.append(item.id);
		builder.append("\"");
		if (item.required) {
			builder.append(" required=\"required\"");
		}
		if (item.maxlength > 0) {
			builder.append(" maxlength=\"");
			builder.append(item.maxlength);
			builder.append("\"");
		}
		builder.append("\">");
		this.appendHtml(builder, item.value);
		builder.append(">\r\n");
	}

}
