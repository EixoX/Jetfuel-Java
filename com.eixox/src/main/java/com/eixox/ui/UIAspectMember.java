package com.eixox.ui;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.globalization.Culture;
import com.eixox.interceptors.InterceptorAspect;
import com.eixox.interceptors.InterceptorList;
import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;
import com.eixox.restrictions.RestrictionAspect;
import com.eixox.restrictions.RestrictionList;

public class UIAspectMember extends AbstractAspectMember {

	private final UIControlType _MemberType;
	private final String _Label;
	private final String _Hint;
	private final String _Placeholder;
	private final UIControlOptionList _Options;
	private final InterceptorList interceptors;
	private final RestrictionList restrictions;
	private final String group;
	private final ValueAdapter<?> adapter;

	public UIAspectMember(AspectMember member, UIControlType memberType, String label, String hint, String placeHolder, UIControlOptionList options, String group) {
		super(member);
		this._MemberType = memberType;
		this._Label = label == null || label.isEmpty() ? member.getName() : label;
		this._Hint = hint;
		this._Placeholder = placeHolder;
		this._Options = options;
		this.group = group;
		this.interceptors = InterceptorAspect.buildInterceptorList(member);
		this.restrictions = RestrictionAspect.buildRestrictionList(member);
		this.adapter = ValueAdapters.getAdapter(member.getDataType());
	}

	public final UIControlType getMemberType() {
		return _MemberType;
	}

	public final String getLabel() {
		return _Label;
	}

	public final String getHint() {
		return _Hint;
	}

	public final String getPlaceholder() {
		return _Placeholder;
	}

	public final UIControlOptionList getOptions() {
		return _Options;
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

	/**
	 * @return the group
	 */
	public final String getGroup() {
		return group;
	}

}
