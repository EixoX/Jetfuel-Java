package com.eixox;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.formatters.ValueFormatter;
import com.eixox.formatters.ValueFormatters;
import com.eixox.globalization.Culture;
import com.eixox.interceptors.InterceptorAspect;
import com.eixox.interceptors.InterceptorList;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;
import com.eixox.restrictions.RestrictionAspect;
import com.eixox.restrictions.RestrictionList;
import com.eixox.ui.UIControl;
import com.eixox.ui.UIControlOptionList;
import com.eixox.ui.UIControlOptionSource;
import com.eixox.ui.UIControlPresentation;
import com.eixox.ui.UIControlState;
import com.eixox.ui.UIControlType;

public class UsecaseAspectMember extends AbstractAspectMember {

	private final UIControlType controlType;
	private final String label;
	private final String hint;
	private final String placeholder;
	private final UIControlOptionList options;
	private final InterceptorList interceptors;
	private final RestrictionList restrictions;
	private final String group;
	private final ValueAdapter<?> adapter;
	private final ValueFormatter<?> formatter;
	private final String formatString;
	private final boolean control;

	private final UIControlOptionList getOptions(Class<?> claz, boolean prependEmptyOption) {
		UIControlOptionList list = null;
		if (claz != null && claz != Object.class) {
			if (UIControlOptionSource.class.isAssignableFrom(claz)) {
				try {
					Object obj = claz.newInstance();
					list = ((UIControlOptionSource) obj).getControlOptions();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
			else {
				throw new RuntimeException("UI Control option source must implement interface UIControlOptionSource");
			}
		}
		if (prependEmptyOption) {
			if (list == null)
				list = new UIControlOptionList();

			list.prepend(null, "");
		}
		return list;
	}

	public UsecaseAspectMember(AspectMember member, UIControl annotation) {
		super(member);
		this.control = annotation != null;
		if (this.control) {
			this.controlType = annotation.type();
			this.label = annotation.label() == null || annotation.label().isEmpty() ? member.getName() : annotation.label();
			this.hint = annotation.hint();
			this.placeholder = annotation.placeholder();
			this.options = getOptions(annotation.source(), annotation.insertEmptyOption());
			this.group = annotation.group();
			this.formatter = ValueFormatters.getFormatter(annotation.formatter());
			this.formatString = annotation.formatString();
		} else {
			this.controlType = UIControlType.HIDDEN;
			this.label = member.getName();
			this.hint = "";
			this.placeholder = "";
			this.options = null;
			this.group = null;
			this.formatter = null;
			this.formatString = null;
		}

		this.interceptors = InterceptorAspect.buildInterceptorList(member);
		this.restrictions = RestrictionAspect.buildRestrictionList(member);
		this.adapter = ValueAdapters.getAdapter(member.getDataType());
	}

	public final UIControlType getControlType() {
		return controlType;
	}

	public final String getLabel() {
		return label;
	}

	public final String getHint() {
		return hint;
	}

	public final String getPlaceholder() {
		return placeholder;
	}

	public final UIControlOptionList getOptions() {
		return options;
	}

	public final InterceptorList getInterceptors() {
		return interceptors;
	}

	public final RestrictionList getRestrictions() {
		return restrictions;
	}

	public final String getGroup() {
		return group;
	}

	public final ValueAdapter<?> getAdapter() {
		return adapter;
	}

	public final ValueFormatter<?> getFormatter() {
		return formatter;
	}

	public final String getFormatString() {
		return formatString;
	}

	public final boolean isControl() {
		return control;
	}

	public final UIControlPresentation buildUIPresentation(UsecaseProperty source, Culture culture) {
		UIControlPresentation presentation = new UIControlPresentation();
		presentation.controlType = this.controlType;
		presentation.hint = this.hint;
		presentation.label = this.label;
		presentation.message = source.message;
		presentation.name = source.name;
		presentation.options = this.options;
		presentation.placeholder = this.placeholder;
		presentation.state = source.state;
		if (source.value == null)
			presentation.value = "";
		else if (this.formatter != null)
			presentation.value = this.formatter.formatObject(source.value, culture);
		else if (this.formatString != null && !this.formatString.isEmpty())
			presentation.value = String.format(culture.getLocale(), this.formatString, source.value);
		else
			presentation.value = source.value.toString();
		return presentation;
	}

	public final void write(UsecaseProperty target) {
		target.name = this.getName();
		target.state = UIControlState.NORMAL;
		target.value = null;
		target.message = null;
	}

	public final void parse(UsecaseProperty source, Object target, Culture culture) {
		Object value = this.interceptors.intercept(source.value);
		if (this.adapter != null)
			value = this.adapter.convert(value, culture);
		setValue(target, value);
	}

	public final boolean validate(Object source, UsecaseProperty target) {
		target.value = getValue(source);
		target.message = this.restrictions.getRestrictionMessageFor(target.value);
		if (target.message != null) {
			target.state = UIControlState.ERROR;
			return false;
		}
		else {
			target.state = UIControlState.SUCCESS;
			return true;
		}
	}

}
