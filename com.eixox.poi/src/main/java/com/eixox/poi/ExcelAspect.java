package com.eixox.poi;

import com.eixox.data.entities.EntityAspect;
import com.eixox.reflection.AspectMember;

public class ExcelAspect extends EntityAspect {

	public ExcelAspect(Class<?> dataType) {
		super(dataType);
	}

	@Override
	protected ExcelAspectMember decorate(AspectMember member) {
		ExcelColumn annotation = member.getAnnotation(ExcelColumn.class);
		return annotation == null ? null : new ExcelAspectMember(member, annotation);
	}

}
