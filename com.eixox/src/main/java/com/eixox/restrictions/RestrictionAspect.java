package com.eixox.restrictions;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class RestrictionAspect extends AbstractAspect<RestrictionAspectMember> {

	private static final HashMap<Class<?>, RestrictionAspect> _Aspects = new HashMap<Class<?>, RestrictionAspect>();

	public static final RestrictionAspect getInstance(Class<?> claz) {
		RestrictionAspect aspect = _Aspects.get(claz);
		if (aspect == null) {
			aspect = new RestrictionAspect(claz);
			_Aspects.put(claz, aspect);
		}
		return aspect;
	}

	private RestrictionAspect(Class<?> claz) {
		super(claz);
	}

	@Override
	protected final RestrictionAspectMember decorate(AspectMember member) {
		RestrictionList restrictions = buildRestrictionList(member);
		return restrictions.size() > 0 ? new RestrictionAspectMember(member, restrictions) : null;
	}

	public final boolean validate(Object instance) {
		if (instance == null)
			return false;

		for (RestrictionAspectMember member : this)
			if (!member.validate(instance))
				return false;

		return true;
	}

	public static RestrictionList buildRestrictionList(AspectMember member) {
		RestrictionList restrictions = new RestrictionList();
		for (Annotation an : member.getAnnotations()) {
			Restriction r = restrictionBuilder.build(an);
			if (r != null)
				restrictions.add(r);
		}
		return restrictions;
	}

	private static RestrictionBuilder restrictionBuilder = RestrictionBuilder.defaultInstance;

	public static void setRestrictionBuilder(RestrictionBuilder builder) {
		if (builder == null)
			throw new RuntimeException("Restriction builder cannot be null");
		else
			restrictionBuilder = builder;
	}
}
