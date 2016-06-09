package com.eixox.poi;

import com.eixox.data.ColumnType;
import com.eixox.data.entities.EntityAspectMember;
import com.eixox.reflection.AspectMember;

public class ExcelAspectMember extends EntityAspectMember {

	public ExcelAspectMember(AspectMember member, ExcelColumn annotation) {
		super(member, ColumnType.REGULAR, annotation.name(), false, false);
	}

}
