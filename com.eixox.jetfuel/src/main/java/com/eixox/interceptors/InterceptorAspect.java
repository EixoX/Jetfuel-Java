package com.eixox.interceptors;

import java.lang.annotation.Annotation;
import java.util.HashMap;

import com.eixox.reflection.AbstractAspect;
import com.eixox.reflection.AspectMember;

public class InterceptorAspect extends AbstractAspect<InterceptorAspectMember> {

	private static InterceptorBuilder interceptorBuilder = InterceptorBuilder.defaultInstance;

	public static void setInterceptorBuilder(InterceptorBuilder b) {
		if (b == null)
			throw new RuntimeException("Interceptor builder cannot be null");
		else
			interceptorBuilder = b;
	}

	public static final InterceptorList buildInterceptorList(AspectMember member) {
		InterceptorList interceptors = new InterceptorList();
		for (Annotation an : member.getAnnotations()) {
			Interceptor i = interceptorBuilder.build(an);
			if (i != null)
				interceptors.add(i);
		}
		return interceptors;
	}

	private static final HashMap<Class<?>, InterceptorAspect> _Aspects = new HashMap<Class<?>, InterceptorAspect>();

	public static final InterceptorAspect getInstance(Class<?> claz) {
		InterceptorAspect aspect = _Aspects.get(claz);
		if (aspect == null) {
			aspect = new InterceptorAspect(claz);
			_Aspects.put(claz, aspect);
		}
		return aspect;
	}

	private InterceptorAspect(Class<?> claz) {
		super(claz);
	}

	@Override
	protected final InterceptorAspectMember decorate(AspectMember member) {
		InterceptorList interceptors = buildInterceptorList(member);
		return interceptors.size() == 0 ? null : new InterceptorAspectMember(member, interceptors);
	}

	public final void apply(Object instance) {

		if (instance != null) {
			for (InterceptorAspectMember member : this) {
				Object value = member.getValue(instance);
				if (value != null) {
					value = member.getInterceptors().intercept(value);
					member.setValue(instance, value);
				}
			}
		}

	}

}
