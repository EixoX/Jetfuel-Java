package com.eixox.interceptors;

import com.eixox.reflection.AspectMember;
import com.eixox.reflection.DecoratedMember;

public class InterceptorAspectMember extends DecoratedMember {

	public InterceptorAspectMember(AspectMember member, InterceptorList interceptors) {
		super(member);
		this._Interceptors = interceptors;
	}

	private final InterceptorList _Interceptors;

	public final InterceptorList getInterceptors() {
		return this._Interceptors;
	}

}
