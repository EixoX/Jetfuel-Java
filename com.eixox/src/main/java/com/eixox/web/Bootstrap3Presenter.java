package com.eixox.web;

import java.util.List;

import com.eixox.html.HtmlBuilder;
import com.eixox.ui.UIControlOption;
import com.eixox.ui.UIControlPresentation;
import com.eixox.ui.UIPresentation;

public final class Bootstrap3Presenter {

	private Bootstrap3Presenter() {
	}

	private static final void openWrapper(HtmlBuilder builder, UIControlPresentation presentation) {
		String wrapperClass = "";
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

	public static String present(UIControlPresentation presentation) {
		HtmlBuilder builder = new HtmlBuilder(255);
		present(builder, presentation);
		return builder.toString();
	}

	public static void present(HtmlBuilder builder, UIControlPresentation presentation) {
		String defaultCss = "form-control  " + presentation.cssClass;
		switch (presentation.controlType) {
		case CHECKBOX:
			String aux = presentation.label;
			presentation.label = "";
			
			openWrapper(builder, presentation);
			builder.openTag("div")
				  .appendAttribute("class", "checkbox " + presentation.cssClass)
				  .openTag("label")
				  .openTag("input")
				  .appendAttribute("type", "checkbox")
				  .appendAttribute("name", presentation.name);
			
			if (Boolean.parseBoolean(presentation.value)) {
				builder.appendAttribute("checked", "");
			}
			
		    builder.appendContent(aux)
				   .closeTag("label")
				   .closeTag("div");
			closeWrapper(builder, presentation);
			break;
		case CHECKBOX_GROUP:
			break;
		case DATE_PICKER:
			openWrapper(builder, presentation);
			builder.openTag("input")
					.appendAttribute("type", "date")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case DROPDOWN:
			openWrapper(builder, presentation);
			builder.openTag("select")
					.appendAttribute("id", presentation.name)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value);
			
			for (UIControlOption option : presentation.options) {
				builder.openTag("option")
					   .appendAttribute("value", option.key);
				
				if (option.key.toString().equalsIgnoreCase(presentation.value))
					builder.appendAttribute("selected", "selected");

				builder.appendContent(option.label)
					   .closeTag("option");
			}
			
			builder.closeTag("select");	
			closeWrapper(builder, presentation);
			break;
		case FILE_UPLOAD:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "file")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case HIDDEN:
			builder.openTag("input").appendAttribute("type", "hidden")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			break;
		case HTML:
			openWrapper(builder, presentation);
			builder.openTag("textarea").appendAttribute("rows", "10")
					.appendAttribute("cols", "40")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendContent(presentation.value).closeTag("textarea");
			closeWrapper(builder, presentation);
			break;
		case PASSWORD:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "password")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case RADIO_GROUP:
			openWrapper(builder, presentation);
			
			for (UIControlOption option : presentation.options) {
				builder.openTag("div")
					   .appendAttribute("class", "radio " + presentation.cssClass)
					   .openTag("label")
					   .openTag("input")
					   .appendAttribute("type", "radio")
					   .appendAttribute("name", presentation.name)
					   .appendAttribute("value", option.key);
				
				if (presentation.value.equalsIgnoreCase(option.key.toString())) {
					builder.appendAttribute("checked", "checked");
				}
				
				builder.appendContent(option.label)
					   .closeTag("label")
					   .closeTag("div");
			}
			
			closeWrapper(builder, presentation);
			break;
		case SINGLE_LINE:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "text")
					.appendAttribute("id", presentation.name)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			closeWrapper(builder, presentation);
			break;
		case MULTI_LINE:
			openWrapper(builder, presentation);
			builder.openTag("textarea").appendAttribute("rows", "10")
					.appendAttribute("cols", "40")
					.appendAttribute("id", presentation.name)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendContent(presentation.value).closeTag("textarea");
			closeWrapper(builder, presentation);
			break;
		case INTEGER:
			openWrapper(builder, presentation);
			builder.openTag("input").appendAttribute("type", "number")
					.appendAttribute("id", presentation.id)
					.appendAttribute("name", presentation.name)
					.appendAttribute("class", defaultCss)
					.appendAttribute("placeholder", presentation.placeholder)
					.appendAttribute("value", presentation.value)
					.closeTag("input");
			closeWrapper(builder, presentation);
			break;
		default:
			throw new RuntimeException("Unknown UI Control type: "
					+ presentation.controlType);
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
