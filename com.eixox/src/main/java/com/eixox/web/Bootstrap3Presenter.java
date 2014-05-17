package com.eixox.web;

import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIPresentation;
import com.eixox.ui.UIPresentationMember;

public final class Bootstrap3Presenter {

	private Bootstrap3Presenter() {
	}

	private static final void openWrapper(HtmlBuilder builder, UIPresentationMember presentation) {
		builder.openTag("div").appendAttribute("class", "form-group").openTag("label").appendAttribute("for", presentation.id).appendContent(presentation.label).closeTag("label");
	}

	private static final void closeWrapper(HtmlBuilder builder, UIPresentationMember presentation) {
		builder.closeTag("div");
	}

	public static void present(HtmlBuilder builder, UIPresentationMember presentation) {
		switch (presentation.controlType) {
		case Checkbox:
			break;
		case CheckboxGroup:
			break;
		case DatePicker:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "date").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case DropDown:
			break;
		case FileUpload:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "file").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case Hidden:
			builder.openTag("input").appendAttribute("type", "hidden").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
			break;
		case Html:
			openWrapper(builder, presentation);
			builder.openTag("textarea").appendAttribute("rows", "10").appendAttribute("cols", "40").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control html-builder")
					.appendAttribute("placeholder", presentation.placeholder).appendContent(presentation.value).closeTag("textarea");
			closeWrapper(builder, presentation);
			break;
		case Password:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "password").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case RadioButton:
			break;
		case RadioGroup:
			break;
		case SingleLine:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "text").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendAttribute("value", presentation.value).closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case Textarea:
			openWrapper(builder, presentation);
			builder.openTag("textarea").appendAttribute("rows", "10").appendAttribute("cols", "40").appendAttribute("id", presentation.id).appendAttribute("name", presentation.name).appendAttribute("class", "form-control")
					.appendAttribute("placeholder", presentation.placeholder).appendContent(presentation.value).closeTag("textarea");
			closeWrapper(builder, presentation);
			break;
		default:
			throw new RuntimeException("Unknown UI Control type: " + presentation.controlType);
		}
	}

	public static String present(UIPresentation<?> presentation) {
		HtmlBuilder builder = new HtmlBuilder(1024);
		int size = presentation.getControlCount();
		for (int i = 0; i < size; i++)
			present(builder, presentation.get(i));
		return builder.toString();

	}

}
