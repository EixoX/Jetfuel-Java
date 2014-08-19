package com.eixox.web;

import java.util.List;

import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIPresentation;
import com.eixox.ui.UIControlPresentation;

public final class Bootstrap3Presenter {

	private Bootstrap3Presenter() {
	}

	private static final void openWrapper(HtmlBuilder builder, UIControlPresentation presentation) {
		String wrapperClass = "form-group";
		switch (presentation.state) {
			case ERROR:
				wrapperClass += " has-error";
				break;
			case SUCCESS:
				wrapperClass += " has-success";
				break;
			case WARNING:
				wrapperClass += " has-warning";
				break;
			default:
				break;
		}
		builder.openTag("div")
				.appendAttribute("class", wrapperClass)
				.openTag("label")
				.appendAttribute("for", presentation.id)
				.appendAttribute("class", "control-label")
				.appendContent(presentation.label)
				.closeTag("label");
	}

	private static final void closeWrapper(HtmlBuilder builder, UIControlPresentation presentation) {
		if (presentation.hint != null && !presentation.hint.isEmpty())
			builder.openTag("p").appendAttribute("class", "help-block").appendContent(presentation.hint).closeTag("p");
		if (presentation.message != null && !presentation.message.isEmpty())
			builder.openTag("p").appendAttribute("class", "help-block").appendContent(presentation.message).closeTag("p");
		builder.closeTag("div");
	}

	public static String present(UIControlPresentation presentation) {
		HtmlBuilder builder = new HtmlBuilder(255);
		present(builder, presentation);
		return builder.toString();
	}

	public static void present(HtmlBuilder builder, UIControlPresentation presentation) {
		switch (presentation.controlType) {
			case CHECKBOX:
				break;
			case CHECKBOX_GROUP:
				break;
			case DATE_PICKER:
				openWrapper(builder, presentation);
				builder.openTag("input").appendAttribute("type", "date").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
				closeWrapper(builder, presentation);
				break;
			case DROPDOWN:
				break;
			case FILE_UPLOAD:
				openWrapper(builder, presentation);
				builder.openTag("input").appendAttribute("type", "file").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
				closeWrapper(builder, presentation);
				break;
			case HIDDEN:
				builder.openTag("input").appendAttribute("type", "hidden").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
				break;
			case HTML:
				openWrapper(builder, presentation);
				builder.openTag("textarea").appendAttribute("rows", "10").appendAttribute("cols", "40").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control html-builder")
						.appendAttribute("placeholder", presentation.placeholder).appendContent(presentation.value).closeTag("textarea");
				closeWrapper(builder, presentation);
				break;
			case PASSWORD:
				openWrapper(builder, presentation);
				builder.openTag("input").appendAttribute("type", "password").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
				closeWrapper(builder, presentation);
				break;
			case RADIO_GROUP:
				break;
			case SINGLE_LINE:
				openWrapper(builder, presentation);
				builder.openTag("input").appendAttribute("type", "text").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
				closeWrapper(builder, presentation);
				break;
			case MULTI_LINE:
				openWrapper(builder, presentation);
				builder.openTag("textarea").appendAttribute("rows", "10").appendAttribute("cols", "40").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.placeholder).appendContent(presentation.value).closeTag("textarea");
				closeWrapper(builder, presentation);
				break;
			default:
				throw new RuntimeException("Unknown UI Control type: " + presentation.controlType);
		}
	}

	public static String present(UIPresentation presentation) {
		HtmlBuilder builder = new HtmlBuilder(1024);
		int size = presentation.getControlCount();
		for (int i = 0; i < size; i++)
			present(builder, presentation.get(i));
		return builder.toString();
	}

	public static String present(List<UIControlPresentation> presentation) {
		HtmlBuilder builder = new HtmlBuilder(1024);
		for (UIControlPresentation item : presentation)
			present(builder, item);
		return builder.toString();
	}

}
