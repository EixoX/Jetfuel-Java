package com.eixox.formatters;

import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class FormatAspect extends AbstractAspect<FormatAspectMember> {

	protected FormatAspect(Class<?> dataType) {
		super(dataType);
	}
	
	@Override
	protected boolean decoratesProperties() {
		return false;
	}

	@Override
	protected FormatAspectMember decorate(AspectMember member) {
		return java.lang.reflect.Modifier.isStatic(member.getModifiers()) ?
				null : new FormatAspectMember(member);
	}

	private static final HashMap<Class<?>, FormatAspect> INSTANCES = new HashMap<Class<?>, FormatAspect>();

	public static synchronized final FormatAspect getInstance(Class<?> claz) {
		FormatAspect aspect = INSTANCES.get(claz);
		if (aspect == null) {
			aspect = new FormatAspect(claz);
			INSTANCES.put(claz, aspect);
		}
		return aspect;
	}

}
