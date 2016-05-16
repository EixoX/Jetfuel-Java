package com.eixox.data.text;

import com.eixox.data.FilterNode;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterType;

public class TextFilterNode implements TextFilter {

	public final TextFilter filter;
	public FilterOperation operation;
	public TextFilterNode next;

	public TextFilterNode(FilterNode node, TextSchema<?> schema) {
		this.filter = TextFilterExpression.transform(node.filter, schema);
		this.operation = node.operation;
		this.next = node.next == null ? null : new TextFilterNode(node.next, schema);
	}

	public TextFilterNode(TextFilterTerm term) {
		this.filter = term;
	}

	public TextFilterNode(TextFilterExpression expression) {
		this.filter = expression;
	}

	public FilterType getFilterType() {
		return FilterType.NODE;
	}

	public boolean filter(Object[] row) {
		if (this.next != null)
			switch (this.operation) {
			case AND:
				return this.filter.filter(row) && this.next.filter(row);
			case OR:
				return this.filter.filter(row) || this.next.filter(row);
			default:
				throw new RuntimeException("Unknown filter operation " + operation);
			}
		else
			return this.filter.filter(row);
	}

}
