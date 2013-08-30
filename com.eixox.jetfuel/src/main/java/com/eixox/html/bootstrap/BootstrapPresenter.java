package com.eixox.html.bootstrap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.eixox.html.HtmlAttribute;
import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIAspect;
import com.eixox.ui.UIControlState;
import com.eixox.ui.UIControlPresenter;

public class BootstrapPresenter implements UIControlPresenter {

	private final HtmlBuilder builder;

	public BootstrapPresenter(int capacity) {
		this.builder = new HtmlBuilder(capacity);
	}

	// Begins the control group and returns an appendable help-inline message
	private final String beginControlGroup(UIControlState state) {
		String helpInline;
		String controlClass;

		if (state.isError()) {
			controlClass = "form-group has-error";
			helpInline = state.getErrorMessage();
		} else if (state.isInfo()) {
			controlClass = "form-group has-warning";
			helpInline = state.getInfoMessage();
		} else if (state.isSuccess()) {
			controlClass = "form-group has-success";
			helpInline = state.getSuccessMessage();
		} else {
			controlClass = "form-group";
			helpInline = state.getHint();
		}

		builder.beginElement("div", controlClass);
		builder.writeSimpleElement("label", state.getLabel(), new HtmlAttribute("class", "control-label"), new HtmlAttribute("for", state.getId()));

		return helpInline;
	}

	private final void closeControlGroup(String helpMessage) {
		if (helpMessage != null && !helpMessage.isEmpty()) {
			builder.writeSimpleElement("p", helpMessage, new HtmlAttribute("class", "help-block"));
		}

		builder.closeElement("div");
	}

	public void presentHidden(UIControlState state) {
		builder.writeElement("input", true, new HtmlAttribute("type", "hidden"), new HtmlAttribute("id", state.getId()),
				new HtmlAttribute("name", state.getName()), new HtmlAttribute("value", state.getValue()));

	}

	public void presentSingleLine(UIControlState state) {
		String helpMessage = beginControlGroup(state);
		builder.writeElement("input", true, new HtmlAttribute("type", "text"), new HtmlAttribute("id", state.getId()),
				new HtmlAttribute("name", state.getName()), new HtmlAttribute("value", state.getValue()), new HtmlAttribute("class", "form-control"));
		closeControlGroup(helpMessage);
	}

	public void presentPassword(UIControlState state) {
		String helpMessage = beginControlGroup(state);
		builder.writeElement("input", true, new HtmlAttribute("type", "password"), new HtmlAttribute("id", state.getId()),
				new HtmlAttribute("name", state.getName()), new HtmlAttribute("value", state.getValue()), new HtmlAttribute("class", "form-control"));
		closeControlGroup(helpMessage);
	}

	public void presentTextarea(UIControlState state) {
		String helpMessage = beginControlGroup(state);
		builder.writeSimpleElement("textarea", state.getValue(), new HtmlAttribute("type", "text"), new HtmlAttribute("id", state.getId()), new HtmlAttribute(
				"name", state.getName()), new HtmlAttribute("class", "form-control"));
		closeControlGroup(helpMessage);
	}

	public void presentHtml(UIControlState state) {
		String helpMessage = beginControlGroup(state);
		builder.writeSimpleElement("textarea", state.getValue(), new HtmlAttribute("type", "text"), new HtmlAttribute("class", "form-control htmlEditor"),
				new HtmlAttribute("id", state.getId()), new HtmlAttribute("name", state.getName()));
		closeControlGroup(helpMessage);
	}

	private static DateFormat datePickerFormat = new SimpleDateFormat("yyyy-mm-dd");

	public void presentDatePicker(UIControlState state) {
		Object value = state.getValue();
		if (value instanceof Calendar)
			value = ((Calendar) value).getTime();

		String valueText = value == null ? "" : datePickerFormat.format((Date) value);
		String helpMessage = beginControlGroup(state);
		builder.writeElement("input", true, new HtmlAttribute("type", "date"), new HtmlAttribute("id", state.getId()),
				new HtmlAttribute("name", state.getName()), new HtmlAttribute("value", valueText), new HtmlAttribute("class", "form-control"));
		closeControlGroup(helpMessage);

	}

	public void presentDropDown(UIControlState state) {
		throw new RuntimeException("Not implemented: " + getClass().getName() + ".presentDropDown");

	}

	public void presentRadioButton(UIControlState state) {
		throw new RuntimeException("Not implemented: " + getClass().getName() + ".presentRadioButton");

	}

	public void presentRadioGroup(UIControlState state) {
		// throw new RuntimeException("Not implemented: " + getClass().getName()
		// + ".presentCheckboxGroup");
		builder.writeRaw("Please implement RadioGroup");

	}

	public void presentCheckbox(UIControlState state) {
		throw new RuntimeException("Not implemented: " + getClass().getName() + ".presentCheckbox");

	}

	public void presentCheckboxGroup(UIControlState state) {
		// throw new RuntimeException("Not implemented: " + getClass().getName()
		// + ".presentCheckboxGroup");
		builder.writeRaw("Please implement CheckboxGroup");
	}

	public void present(UIControlState state) {

		switch (state.getMemberType()) {
		case Checkbox:
			presentCheckbox(state);
			break;
		case CheckboxGroup:
			presentCheckboxGroup(state);
			break;
		case DatePicker:
			presentDatePicker(state);
			break;
		case DropDown:
			presentDropDown(state);
			break;
		case Hidden:
			presentHidden(state);
			break;
		case Html:
			presentHtml(state);
			break;
		case Password:
			presentPassword(state);
			break;
		case RadioButton:
			presentRadioButton(state);
			break;
		case RadioGroup:
			presentRadioGroup(state);
			break;
		case SingleLine:
			presentSingleLine(state);
			break;
		case Textarea:
			presentTextarea(state);
			break;
		default:
			throw new RuntimeException(this.getClass().getName() + " can't present " + state.getMemberType());
		}
	}

	public String present(Object instance, boolean validate) {
		UIAspect aspect = UIAspect.getDefaultInstance(instance.getClass());
		aspect.present(instance, validate, this);
		return builder.toString();
	}
}
