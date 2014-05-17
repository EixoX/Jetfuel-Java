package com.eixox.text;

import com.eixox.data.DataAspectMember;
import com.eixox.interceptors.InterceptorAspect;
import com.eixox.interceptors.InterceptorList;
import com.eixox.reflection.AspectMember;

public class CsvAspectMember extends DataAspectMember {

	private final InterceptorList interceptors;

	public CsvAspectMember(AspectMember member, CsvColumn column) {
		super(member, column.dataName(), true, column.caption());
		this.interceptors = InterceptorAspect.buildInterceptorList(member);
	}

	public final InterceptorList getInterceptors() {
		return interceptors;
	}

	public final void setCsvValue(Object instance, String value) {
		Object v = this.interceptors.intercept(value);
		super.setDataValue(instance, v);
	}

}
