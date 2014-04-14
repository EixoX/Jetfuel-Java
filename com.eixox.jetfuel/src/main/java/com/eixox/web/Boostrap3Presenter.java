package com.eixox.web;

import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIControlPresentation;

public class Boostrap3Presenter {

	public void present(HtmlBuilder builder, UIControlPresentation presentation) {
		switch (presentation.getControlType()) {
			case Checkbox:
				break;
			case CheckboxGroup:
				break;
			case DatePicker:
				break;
			case DropDown:
				break;
			case FileUpload:
				break;
			case Hidden:
				break;
			case Html:
				break;
			case Password:
				break;
			case RadioButton:
				break;
			case RadioGroup:
				break;
			case SingleLine:
				builder.openTag("div").appendAttribute("class", "form-group")
						.openTag("label").appendAttribute("for", presentation.getId()).appendContent(presentation.getLabel()).closeTag("label")
						.openTag("input").appendAttribute("id", presentation.getId()).appendAttribute("name", presentation.getName()).appendAttribute("class", "form-control")
						.appendAttribute("placeholder", presentation.getPlaceholder()).appendAttribute("value", presentation.getValue()).closeTag("input")
						.closeTag("div");
				break;
			case Textarea:
				break;
			default:
				throw new RuntimeException("Unknown UI Control type: " + presentation.getControlType());
		}
	}

	private void present() {

	}

}
