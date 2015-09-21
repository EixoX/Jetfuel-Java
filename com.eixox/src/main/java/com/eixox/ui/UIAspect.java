package com.eixox.ui;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class UIAspect extends AbstractAspect<UIAspectMember> {

	protected UIAspect(Class<?> dataType) {
		super(dataType);
	}

	@Override
	protected UIAspectMember decorate(AspectMember member) {
		UIAspectMember uimember = new UIAspectMember(member);
		return uimember.controlType == ControlType.NONE ? null : uimember;
	}

}
