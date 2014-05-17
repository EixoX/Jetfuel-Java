package com.eixox.data;

import java.util.ArrayList;

public final class SelectResult<E> extends ArrayList<E> {

	private static final long serialVersionUID = 6025264851251173015L;
	private final Select<E> originalSelect;

	public SelectResult(Select<E> originalSelect) {
		this.originalSelect = originalSelect;
	}

	public SelectResult(Select<E> originalSelect, int capacity) {
		super(capacity);
		this.originalSelect = originalSelect;
	}

	public final Select<E> getOriginalSelect() {
		return originalSelect;
	}

	public final SelectResult<E> nextPage() {
		this.originalSelect.setPageOrdinal(this.originalSelect.getPageOrdinal() + 1);
		return this.originalSelect.getResult();
	}

	public final SelectResult<E> previousPage() {
		this.originalSelect.setPageOrdinal(this.originalSelect.getPageOrdinal() - 1);
		return this.originalSelect.getResult();
	}
}
