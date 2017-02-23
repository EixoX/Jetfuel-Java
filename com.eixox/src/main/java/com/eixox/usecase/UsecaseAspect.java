package com.eixox.usecase;

import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class UsecaseAspect extends AbstractAspect<UsecaseAspectMember> {

	private static final HashMap<Class<?>, UsecaseAspect> instances = new HashMap<Class<?>, UsecaseAspect>();

	@Override
	protected boolean decoratesProperties() {
		return false;
	}

	@Override
	protected boolean decoratesParent() {
		return true;
	}

	private UsecaseAspect(Class<?> dataType) {
		super(dataType);
	}

	@Override
	protected UsecaseAspectMember decorate(AspectMember member) {

		if (member.isReadOnly() || Modifier.isStatic(member.getModifiers()))
			return null;
		else
			return new UsecaseAspectMember(member);
	}

	public static synchronized final UsecaseAspect getInstance(Class<?> claz) {
		UsecaseAspect aspect = instances.get(claz);
		if (aspect == null) {
			aspect = new UsecaseAspect(claz);
			instances.put(claz, aspect);
		}
		return aspect;
	}
	

}
