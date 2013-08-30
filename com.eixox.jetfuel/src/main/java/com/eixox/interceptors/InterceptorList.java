package com.eixox.interceptors;

import java.util.ArrayList;

public class InterceptorList extends ArrayList<Interceptor> implements Interceptor {

	private static final long serialVersionUID = 8477298505128170581L;

	@Override
	public final Object intercept(Object input) {
		if (size() > 0)
			for (Interceptor i : this)
				input = i.intercept(input);
		return input;
	}
}
