package com.eixox.html.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Locale;

import com.eixox.Convert;
import com.eixox.html.HtmlAttribute;
import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIAspect;
import com.eixox.ui.UIControlOption;
import com.eixox.ui.UIControlOptionList;
import com.eixox.ui.UIControlPresentation;
import com.eixox.ui.UIControlPresenter;

public class Bootstrap3Presenter implements UIControlPresenter {

	private final UIAspect aspect;
	private HtmlBuilder builder;
	private String labelClass = "control-label";
	private String controlClass = "form-control";
	private String groupClass = "form-group";
	private boolean controlWrappingDiv = false;
	private String controlWrappingDivClass;

	public Bootstrap3Presenter(Class<?> claz) {
		this(UIAspect.getDefaultInstance(claz));
	}

	public Bootstrap3Presenter(UIAspect aspect) {
		this.aspect = aspect;
	}

	private void beginControlGroup(UIControlPresentation presentation) {

		String gClass = this.groupClass;

		switch (presentation.getState()) {
		case Error:
			gClass = this.groupClass == null || this.groupClass.isEmpty() ? "has-error" : this.groupClass + " has-error";
			break;
		case Normal:
			break;
		case Success:
			gClass = this.groupClass == null || this.groupClass.isEmpty() ? "has-success" : this.groupClass + " has-success";
			break;
		case Warning:
			gClass = this.groupClass == null || this.groupClass.isEmpty() ? "has-warning" : this.groupClass + " has-warning";
			break;
		default:
			break;

		}

		builder.beginElement("div", gClass == null ? "" : gClass);
		builder.writeSimpleElement("label", presentation.getLabel(), new HtmlAttribute("for", presentation.getId()),
				new HtmlAttribute("class", this.labelClass));
		if (this.controlWrappingDiv)
			builder.beginElement("div", this.controlWrappingDivClass);
	}

	private void endControlGroup(UIControlPresentation presentation) {
		if (this.controlWrappingDiv)
			builder.closeElement("div");
		if (presentation.getMessage() != null && !presentation.getMessage().isEmpty()) {
			builder.writeSimpleElement("p", presentation.getMessage(), new HtmlAttribute("class", "help-text"));
		} else if (presentation.getHint() != null && !presentation.getHint().isEmpty()) {
			builder.writeSimpleElement("p", presentation.getHint(), new HtmlAttribute("class", "help-text"));
		}
		builder.closeElement("div");

	}

	private void presentCheckbox(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		this.builder.beginElement("div", "checkbox");
		this.builder.beginElement("label", this.labelClass);

		if (Convert.toBoolean(presentation.getValue()))
			this.builder.writeElement("input", true, new HtmlAttribute("type", "checkbox"), new HtmlAttribute("name", presentation.getName()),
					new HtmlAttribute("id", presentation.getId()), new HtmlAttribute("value", "true"), new HtmlAttribute("checked", "checked"));
		else
			this.builder.writeElement("input", true, new HtmlAttribute("type", "checkbox"), new HtmlAttribute("name", presentation.getName()),
					new HtmlAttribute("id", presentation.getId()), new HtmlAttribute("value", "true"));

		this.builder.writeText(presentation.getLabel());

		this.builder.closeElement("label");
		this.builder.closeElement("div");

		endControlGroup(presentation);
	}

	private void presentCheckboxGroup(UIControlPresentation presentation) {
		builder.writeText("Please write the presentCheckboxGroup()");
	}

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private void presentDatePicker(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		String value = presentation.getValue() == null ? "" : sdf.format(presentation.getValue());

		builder.writeElement("input", true, new HtmlAttribute("type", "date"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("id",
				presentation.getId()), new HtmlAttribute("value", value), new HtmlAttribute("placeholder", presentation.getPlaceholder()), new HtmlAttribute(
				"class", "datepicker " + this.controlClass));
		endControlGroup(presentation);
	}

	private void presentDropDown(UIControlPresentation presentation) {
		beginControlGroup(presentation);

		builder.beginElement("select", new HtmlAttribute("class", this.controlClass), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute(
				"id", presentation.getId()));

		builder.writeSimpleElement("option", "-", new HtmlAttribute("value", ""));

		Object value = presentation.getValue();
		UIControlOptionList options = presentation.getOptions();
		if (options != null)
			for (UIControlOption option : options) {
				if (value != null && value.equals(option.getKey())) {
					builder.writeSimpleElement("option", option.getValue(), new HtmlAttribute("value", option.getKey()), new HtmlAttribute("selected",
							"selected"));
				} else {
					builder.writeSimpleElement("option", option.getValue(), new HtmlAttribute("value", option.getKey()));
				}
			}

		builder.closeElement("select");

		endControlGroup(presentation);
	}

	private void presentHtml(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		builder.writeSimpleElement("textarea", presentation.getValue(), new HtmlAttribute("id", presentation.getId()),
				new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("rows", "10"), new HtmlAttribute("cols", "40"), new HtmlAttribute("class",
						"htmlEditor " + this.controlClass), new HtmlAttribute("placeholder", presentation.getPlaceholder()));
		endControlGroup(presentation);
	}

	private void presentPassword(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		builder.writeElement("input", true, new HtmlAttribute("type", "password"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("id",
				presentation.getId()), new HtmlAttribute("value", presentation.getValue()), new HtmlAttribute("placeholder", presentation.getPlaceholder()),
				new HtmlAttribute("class", this.controlClass));
		endControlGroup(presentation);
	}

	private void presentRadioButton(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		this.builder.beginElement("div", "checkbox");
		this.builder.beginElement("label", this.labelClass);

		if (Convert.toBoolean(presentation.getValue()))
			this.builder.writeElement("input", true, new HtmlAttribute("type", "radio"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute(
					"id", presentation.getId()), new HtmlAttribute("value", "true"), new HtmlAttribute("checked", "checked"));
		else
			this.builder.writeElement("input", true, new HtmlAttribute("type", "radio"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute(
					"id", presentation.getId()), new HtmlAttribute("value", "true"));

		this.builder.writeText(presentation.getLabel());

		this.builder.closeElement("label");
		this.builder.closeElement("div");

		endControlGroup(presentation);
	}

	private void presentRadioGroup(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		Object value = presentation.getValue();
		for (UIControlOption option : presentation.getOptions()) {
			builder.beginElement("div", "radio");
			builder.beginElement("label");

			if (value != null && value.equals(Convert.changeType(value.getClass(), option.getKey(), Locale.ENGLISH))) {
				builder.writeElement("input", true, new HtmlAttribute("type", "radio"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute(
						"id", presentation.getName() + "_" + option.getKey()), new HtmlAttribute("value", option.getKey()), new HtmlAttribute("checked",
						"checked"));
			} else {
				builder.writeElement("input", true, new HtmlAttribute("type", "radio"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute(
						"id", presentation.getName() + "_" + option.getKey()), new HtmlAttribute("value", option.getKey()));
			}

			builder.writeText(option.getValue());
			builder.closeElement("label");
			builder.closeElement("div");

		}
		endControlGroup(presentation);
	}

	private void presentSingleline(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		builder.writeElement("input", true, new HtmlAttribute("type", "text"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("id",
				presentation.getId()), new HtmlAttribute("value", presentation.getValue()), new HtmlAttribute("placeholder", presentation.getPlaceholder()),
				new HtmlAttribute("class", this.controlClass));
		endControlGroup(presentation);
	}

	private void presentTextarea(UIControlPresentation presentation) {

		beginControlGroup(presentation);
		builder.writeSimpleElement("textarea", presentation.getValue(), new HtmlAttribute("id", presentation.getId()),
				new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("rows", "10"), new HtmlAttribute("cols", "40"), new HtmlAttribute("class",
						this.controlClass), new HtmlAttribute("placeholder", presentation.getPlaceholder()));
		endControlGroup(presentation);
	}

	private void presentHidden(UIControlPresentation presentation) {
		builder.writeElement("input", true, new HtmlAttribute("type", "hidden"), new HtmlAttribute("id", presentation.getId()), new HtmlAttribute("name",
				presentation.getName()), new HtmlAttribute("value", presentation.getValue()));
	}

	private void presentFileUpload(UIControlPresentation presentation) {
		beginControlGroup(presentation);
		builder.writeElement("input", true, new HtmlAttribute("type", "file"), new HtmlAttribute("name", presentation.getName()), new HtmlAttribute("id",
				presentation.getId()), new HtmlAttribute("value", presentation.getValue()), new HtmlAttribute("placeholder", presentation.getPlaceholder()),
				new HtmlAttribute("class", this.controlClass));
		endControlGroup(presentation);
	}

	public void present(UIControlPresentation presentation) {

		switch (presentation.getControlType()) {
		case Checkbox:
			presentCheckbox(presentation);
			break;
		case CheckboxGroup:
			presentCheckboxGroup(presentation);
			break;
		case DatePicker:
			presentDatePicker(presentation);
			break;
		case DropDown:
			presentDropDown(presentation);
			break;
		case Html:
			presentHtml(presentation);
			break;
		case Password:
			presentPassword(presentation);
			break;
		case RadioButton:
			presentRadioButton(presentation);
			break;
		case RadioGroup:
			presentRadioGroup(presentation);
			break;
		case SingleLine:
			presentSingleline(presentation);
			break;
		case Textarea:
			presentTextarea(presentation);
			break;
		case Hidden:
			presentHidden(presentation);
			break;
		case FileUpload:
			presentFileUpload(presentation);
			break;
		default:
			break;

		}
	}

	public String presentControl(Object instance, boolean validate, String name) {
		this.builder = new HtmlBuilder(255);
		UIControlPresentation presentation = this.aspect.get(name).toPresentation(instance, validate);
		present(presentation);
		return builder.toString();
	}

	public String presentControls(Object instance, boolean validate, String groupName) {
		this.builder = new HtmlBuilder(255);
		this.aspect.present(instance, groupName, validate, this);
		return builder.toString();
	}

	public String presentControls(Object instance, boolean validate) {
		this.builder = new HtmlBuilder(255);
		this.aspect.present(instance, validate, this);
		return builder.toString();
	}

}
