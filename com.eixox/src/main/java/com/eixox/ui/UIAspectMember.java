package com.eixox.ui;

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

public class UIAspectMember extends AbstractAspectMember {

	private final UIControlType memberType;
	private final String label;
	private final String hint;
	private final String placeholder;
	private final UIControlOptionList options;
	private final InterceptorList interceptors;
	private final RestrictionList restrictions;
	private final String group;
	private final ValueAdapter<?> adapter;
	private final boolean insertEmptyOption;
	private final ValueFormatter<?> formatter;

	private static UIControlOptionList getOptions(Class<?> claz) {
		return null;
	}

	public UIAspectMember(AspectMember member, UIControl annotation) {
		super(member);
		this.memberType = annotation.type();
		this.label = annotation.label() == null || annotation.label().isEmpty() ? member.getName() : annotation.label();
		this.hint = annotation.hint();
		this.placeholder = annotation.placeholder();
		this.options = getOptions(annotation.source());
		this.group = annotation.group();
		this.interceptors = InterceptorAspect.buildInterceptorList(member);
		this.restrictions = RestrictionAspect.buildRestrictionList(member);
		this.adapter = ValueAdapters.getAdapter(member.getDataType());
		this.insertEmptyOption = annotation.insertEmptyOption();
		this.formatter = ValueFormatters.getFormatter(annotation.formatter(), annotation.formatString());
	}

	public final UIControlType getMemberType() {
		return memberType;
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

	public final boolean validate(Object instance) {
		Object value = getValue(instance);
		value = this.interceptors.intercept(instance);
		return this.restrictions.validate(value);
	}

	public final String getRestrictionMessageFor(Object instance) {
		Object value = getValue(instance);
		value = this.interceptors.intercept(value);
		return this.restrictions.getRestrictionMessageFor(value);
	}

	public final Object parse(String content, Object destination, Culture culture) {
		Object value = this.interceptors.intercept(content);
		value = this.adapter == null ? value : this.adapter.convert(value);
		super.setValue(destination, value);
		return value;
	}

	public final String read(Object entity, Culture culture) {
		Object value = super.getValue(entity);
		if (formatter == null)
			return value == null ? "" : value.toString();
		else
			return formatter.formatObject(value, culture);
	}

	public final boolean getInsertEmptyOption() {
		return this.insertEmptyOption;
	}

	/**
	 * @return the group
	 */
	public final String getGroup() {
		return group;
	}

}
