package com.eixox.ui;

import java.util.HashMap;
import java.util.Locale;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class UIAspect extends AbstractAspect<UIAspectMember> {

	public UIAspect(Class<?> claz, Locale locale) {
		super(claz);
	}

	@Override
	protected final UIAspectMember decorate(AspectMember member) {
		UIControl ui = member.getAnnotation(UIControl.class);
		if (ui != null) {
			return new UIAspectMember(member, ui);
		} else {
			return null;
		}
	}

	private static final HashMap<Class<?>, UIAspect> _DefaultInstances = new HashMap<Class<?>, UIAspect>();

	public static synchronized final UIAspect getDefaultInstance(Class<?> claz) {
		UIAspect aspect = _DefaultInstances.get(claz);
		if (aspect == null) {
			aspect = new UIAspect(claz, Locale.ENGLISH);
			_DefaultInstances.put(claz, aspect);
		}
		return aspect;
	}

}
