package com.eixox.ui;

import java.util.List;

import com.eixox.interceptors.InterceptorAspect;
import com.eixox.interceptors.InterceptorList;
import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;
import com.eixox.restrictions.RestrictionAspect;
import com.eixox.restrictions.RestrictionList;

public class UIAspectMember extends DecoratedMember {

	private final UIControlType _MemberType;
	private final String _Label;
	private final String _Hint;
	private final String _Placeholder;
	private final List<UIControlOption> _Options;
	private final InterceptorList interceptors;
	private final RestrictionList restrictions;

	public UIAspectMember(AspectMember member, UIControlType memberType, String label, String hint,
			String placeHolder, List<UIControlOption> options) {
		super(member);
		this._MemberType = memberType;
		this._Label = label == null || label.isEmpty() ? member.getName() : label;
		this._Hint = hint;
		this._Placeholder = placeHolder;
		this._Options = options;
		this.interceptors = InterceptorAspect.buildInterceptorList(member);
		this.restrictions = RestrictionAspect.buildRestrictionList(member);
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

	public final List<UIControlOption> getOptions() {
		return _Options;
	}

	public final InterceptorList getInterceptors() {
		return interceptors;
	}

	public final RestrictionList getRestrictions() {
		return restrictions;
	}

	public final boolean validate(Object instance){
		Object value = getValue(instance);
		value = this.interceptors.intercept(instance);
		return this.restrictions.validate(value);
	}
	
	public final String getRestrictionMessageFor(Object instance){
		Object value = getValue(instance);
		value = this.interceptors.intercept(value);
		return this.restrictions.getRestrictionMessageFor(value);
	}
}
