package com.eixox.interceptors;

import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class InterceptorAspectMember extends AbstractAspectMember {

	public InterceptorAspectMember(AspectMember member, InterceptorList interceptors) {
		super(member);
		this._Interceptors = interceptors;
	}

	private final InterceptorList _Interceptors;

	public final InterceptorList getInterceptors() {
		return this._Interceptors;
	}

}
