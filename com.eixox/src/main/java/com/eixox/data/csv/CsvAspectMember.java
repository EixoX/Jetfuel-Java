package com.eixox.data.csv;

import com.eixox.reflection.AbstractAspectMember;
import com.eixox.reflection.AspectMember;

public class CsvAspectMember extends AbstractAspectMember {

	public final boolean ignoreParseErrors;

	public CsvAspectMember(AspectMember member, CsvColumn annotation) {
		super(member);

		this.ignoreParseErrors = annotation.ignoreParseErrors();
	}

}
