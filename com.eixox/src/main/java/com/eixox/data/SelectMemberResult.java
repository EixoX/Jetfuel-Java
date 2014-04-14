package com.eixox.data;

import java.util.ArrayList;

public final class SelectMemberResult extends ArrayList<Object> {

	private static final long	serialVersionUID	= 6025264851251173015L;
	private final SelectMember	originalSelectMember;

	public SelectMemberResult(SelectMember originalSelectMember) {
		this.originalSelectMember = originalSelectMember;
	}

	public SelectMemberResult(SelectMember originalSelectMember, int capacity) {
		super(capacity);
		this.originalSelectMember = originalSelectMember;
	}

	public final SelectMember getOriginalSelectMember() {
		return originalSelectMember;
	}

	public final SelectMemberResult nextPage() {
		this.originalSelectMember.setPageOrdinal(this.originalSelectMember.getPageOrdinal() + 1);
		return this.originalSelectMember.getResult();
	}

	public final SelectMemberResult previousPage() {
		this.originalSelectMember.setPageOrdinal(this.originalSelectMember.getPageOrdinal() - 1);
		return this.originalSelectMember.getResult();
	}

}
