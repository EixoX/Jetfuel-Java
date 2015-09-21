package com.eixox;

import java.lang.annotation.Annotation;

import com.eixox.adapters.ValueAdapter;
import com.eixox.adapters.ValueAdapters;
import com.eixox.globalization.Culture;
import com.eixox.interceptors.Interceptor;
import com.eixox.interceptors.InterceptorBuilder;
import com.eixox.interceptors.InterceptorList;
import com.eixox.reflection.AspectMember;
import com.eixox.restrictions.Restriction;
import com.eixox.restrictions.RestrictionBuilder;
import com.eixox.restrictions.RestrictionList;
import com.eixox.ui.UIAspectMember;

public class UsecaseAspectMember extends UIAspectMember {

	public final InterceptorList interceptors;
	public final RestrictionList restrictions;
	public final UIAspectMember ui;
	public final ValueAdapter<?> adapter;

	public static InterceptorBuilder INTERCEPTOR_BUILDER = InterceptorBuilder.defaultInstance;
	public static RestrictionBuilder RESTRICTION_BUILDER = RestrictionBuilder.defaultInstance;

	public UsecaseAspectMember(AspectMember member) {
		super(member);

		this.interceptors = new InterceptorList();
		this.restrictions = new RestrictionList();
		this.ui = new UIAspectMember(member);
		this.adapter = ValueAdapters.getAdapter(member.getDataType());

		for (Annotation an : member.getAnnotations()) {
			Interceptor interceptor = INTERCEPTOR_BUILDER.build(an);
			if (interceptor != null)
				this.interceptors.add(interceptor);
			else {
				Restriction restriction = RESTRICTION_BUILDER.build(an);
				if (restriction != null)
					this.restrictions.add(restriction);
			}
		}
	}

	public final boolean validate(Object instance) {
		if (this.restrictions.size() == 0)
			return true;
		else {
			Object value = this.getValue(instance);
			return this.restrictions.validate(value);
		}
	}

	public final void parse(Object instance, Culture culture, String value) {
		String v = (String) this.interceptors.intercept(value);
		Object output = this.adapter != null ? this.adapter.parse(culture, v) : v;
		super.setValue(instance, output);
	}

}
