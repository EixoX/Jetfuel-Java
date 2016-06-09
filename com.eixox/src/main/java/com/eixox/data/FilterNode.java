package com.eixox.data;

import java.util.List;

public class FilterNode implements Filter {

	public final Filter filter;
	public FilterOperation operation;
	public FilterNode next;

	public FilterNode(Filter filter) {
		this.filter = filter;
	}

	public final FilterType getFilterType() {
		return FilterType.NODE;
	}

	public final boolean evaluate(List<String> cols, Object[] row) {
		if (this.next == null)
			return this.filter.evaluate(cols, row);
		else
			switch (this.operation) {
			case AND:
				return filter.evaluate(cols, row) && next.evaluate(cols, row);
			case OR:
				return filter.evaluate(cols, row) || next.evaluate(cols, row);
			default:
				throw new RuntimeException("Unknwon filter op " + this.operation);
			}
	}
}
