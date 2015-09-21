package com.eixox.web;

import java.util.List;

import com.eixox.Convert;
import com.eixox.html.HtmlBuilder;
import com.eixox.ui.Option;
import com.eixox.ui.UIPresentation;
import com.eixox.ui.UIPresentationMember;

public final class Bootstrap3Presenter {

	private Bootstrap3Presenter() {
	}

	private static void openWrapper(HtmlBuilder builder, UIPresentationMember presentation) {
		String wrapperClass = "form-group ";

		switch (presentation.controlState) {
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
				.appendAttribute("data-control-id", presentation.id);
	}

	private static void appendLabel(HtmlBuilder builder, UIPresentationMember presentation) {

		builder.openTag("label")
				.appendAttribute("for", presentation.id)
				.appendAttribute("class", "control-label")
				.appendContent(presentation.label)
				.closeTag("label");
	}

	private static void closeWrapper(HtmlBuilder builder, UIPresentationMember presentation) {
		if (presentation.hint != null && !presentation.hint.isEmpty())
			builder.openTag("p")
					.appendAttribute("class", "help-block")
					.appendContent(presentation.hint)
					.closeTag("p");

		if (presentation.message != null && !presentation.message.isEmpty())
			builder.openTag("p")
					.appendAttribute("class", "help-block")
					.appendContent(presentation.message)
					.closeTag("p");

		builder.closeTag("div");
	}

	private static void presentSimpleInput(HtmlBuilder builder, UIPresentationMember presentation, String inputType) {

		openWrapper(builder, presentation);
		appendLabel(builder, presentation);

		builder.openTag("input")
				.appendAttribute("type", inputType)
				.appendAttribute("id", presentation.id)
				.appendAttribute("name", presentation.name)
				.appendAttribute("class", presentation.appendCss == null ? "form-control" : "form-control " + presentation.appendCss)
				.appendAttribute("placeholder", presentation.placeholder)
				.appendAttribute("value", presentation.value);

		if (presentation.required)
			builder.appendAttribute("required", "required");

		if (presentation.maxlength > 0)
			builder.appendAttribute("maxlength", presentation.maxlength);

		builder.closeTag("input");

		closeWrapper(builder, presentation);
	}

	private static void presentTextarea(HtmlBuilder builder, UIPresentationMember presentation, String itemCssClass) {
		openWrapper(builder, presentation);
		appendLabel(builder, presentation);

		builder.openTag("textarea")
				.appendAttribute("rows", "10")
				.appendAttribute("cols", "40")
				.appendAttribute("id", presentation.id)
				.appendAttribute("name", presentation.name)
				.appendAttribute("class", "form-control " + itemCssClass)
				.appendAttribute("placeholder", presentation.placeholder);

		if (presentation.required)
			builder.appendAttribute("required", "required");

		if (presentation.maxlength > 0)
			builder.appendAttribute("maxlength", presentation.maxlength);

		builder.appendContent(presentation.value);
		builder.closeTag("textarea");

		closeWrapper(builder, presentation);
	}

	private static void presentCheckbox(HtmlBuilder builder, String name, boolean checked, String value, String label) {

		builder
				.openTag("div")
				.appendAttribute("class", "checkbox");
		builder
				.openTag("label")
				.openTag("input")
				.appendAttribute("type", "checkbox")
				.appendAttribute("name", name)
				.appendAttribute("value", value);

		if (checked)
			builder.appendAttribute("checked", "checked");

		builder
				.closeTag("input")
				.appendContent(label)
				.closeTag("label")
				.closeTag("div");
	}

	private static void presentRadio(HtmlBuilder builder, String name, boolean checked, String value, String label) {

		builder
				.openTag("div")
				.appendAttribute("class", "radio");
		builder
				.openTag("label")
				.openTag("input")
				.appendAttribute("type", "radio")
				.appendAttribute("name", name)
				.appendAttribute("value", value);

		if (checked)
			builder.appendAttribute("checked", "checked");

		builder
				.closeTag("input")
				.appendContent(label)
				.closeTag("label")
				.closeTag("div");
	}

	private static void presentCheckboxGroup(HtmlBuilder builder, UIPresentationMember presentation) {
		openWrapper(builder, presentation);
		appendLabel(builder, presentation);

		if (presentation.options != null) {
			String[] presKeys = presentation.value == null ? null : presentation.value.split(",");

			for (Option opt : presentation.options) {
				boolean selected = false;
				for (int i = 0; i < presKeys.length && selected == false; i++)
					if (presKeys[i].equals(opt.key))
						selected = true;

				presentCheckbox(builder, presentation.name, selected, opt.key == null ? opt.label : opt.key.toString(), opt.label);
			}
		}

		closeWrapper(builder, presentation);
	}

	private static void presentRadioGroup(HtmlBuilder builder, UIPresentationMember presentation) {
		openWrapper(builder, presentation);
		appendLabel(builder, presentation);

		if (presentation.options != null) {
			for (Option opt : presentation.options) {
				boolean selected = presentation.value != null && presentation.value.equals(opt.key);
				presentRadio(builder, presentation.name, selected, opt.key == null ? opt.label : opt.key.toString(), opt.label);
			}
		}

		closeWrapper(builder, presentation);
	}

	private static void presentDropdown(HtmlBuilder builder, UIPresentationMember presentation) {
		openWrapper(builder, presentation);
		appendLabel(builder, presentation);

		builder.openTag("select")
				.appendAttribute("id", presentation.id)
				.appendAttribute("name", presentation.name)
				.appendAttribute("class", presentation.appendCss == null ? "form-control" : "form-control " + presentation.appendCss)
				.appendAttribute("placeholder", presentation.placeholder)
				.appendAttribute("value", presentation.value);

		if (presentation.required)
			builder.appendAttribute("required", "required");

		if (presentation.maxlength > 0)
			builder.appendAttribute("maxlength", presentation.maxlength);

		if (presentation.options != null)
			for (Option opt : presentation.options) {
				builder
						.openTag("option")
						.appendAttribute("value", opt.key);

				if (presentation.value != null && presentation.value.equals(opt.key))
					builder.appendAttribute("selected", "selected");

				builder
						.appendContent(opt.label)
						.closeTag("option");
			}

		builder.closeTag("select");

		closeWrapper(builder, presentation);
	}

	public static String present(UIPresentationMember presentation) {
		HtmlBuilder builder = new HtmlBuilder(255);
		present(builder, presentation);
		return builder.toString();
	}

	public static void present(HtmlBuilder builder, UIPresentationMember presentation) {

		switch (presentation.controlType) {
			case CHECKBOX:
				presentCheckbox(builder, presentation.name, Convert.toBoolean(presentation.value), "true", presentation.label);
				break;
			case CHECKBOX_GROUP:
				presentCheckboxGroup(builder, presentation);
				break;
			case DATE:
				presentSimpleInput(builder, presentation, "date");
				break;
			case DROPDOWN:
				presentDropdown(builder, presentation);
				break;
			case FILE:
				presentSimpleInput(builder, presentation, "file");
				break;
			case HIDDEN:
				builder.openTag("input").appendAttribute("type", "hidden")
						.appendAttribute("id", presentation.id)
						.appendAttribute("name", presentation.name)
						.appendAttribute("value", presentation.value)
						.closeTag("input");
				break;
			case HTML:
				presentTextarea(builder, presentation, "html-editor");
				break;
			case PASSWORD:
				presentSimpleInput(builder, presentation, "password");
				break;
			case RADIO_GROUP:
				presentRadioGroup(builder, presentation);
				break;
			case SINGLE_LINE:
				presentSimpleInput(builder, presentation, "text");
				break;
			case MULTILINE:
				presentTextarea(builder, presentation, "");
				break;
			case NUMERIC:
				presentSimpleInput(builder, presentation, "number");
				break;
			case EMAIL:
				presentSimpleInput(builder, presentation, "email");
				break;
			default:
				break;
		}
	}

	public static String present(UIPresentation presentation) {
		HtmlBuilder builder = new HtmlBuilder(1024);
		int size = presentation.size();
		for (int i = 0; i < size; i++)
			present(builder, presentation.get(i));
		return builder.toString();
	}

	public static String present(List<UIPresentationMember> presentation) {
		HtmlBuilder builder = new HtmlBuilder(1024);
		for (UIPresentationMember item : presentation)
			present(builder, item);
		return builder.toString();
	}

}
