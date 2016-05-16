package com.eixox.data.text;

import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterTerm;
import com.eixox.data.FilterType;

public class TextFilterExpression implements TextFilter {

	public TextFilterNode first;
	public TextFilterNode last;

	public TextFilterExpression(FilterExpression expression, TextSchema<?> schema) {
		this.first = new TextFilterNode(expression.first, schema);
		this.last = this.first;
	}

	public TextFilterExpression(TextFilterTerm term) {
		this.first = new TextFilterNode(term);
		this.last = this.first;
	}

	public TextFilterExpression(TextFilterExpression expression) {
		this.first = new TextFilterNode(expression);
		this.last = this.first;
	}

	public FilterType getFilterType() {
		return FilterType.EXPRESSION;
	}

	public boolean filter(Object[] row) {
		return this.first == null ? false : this.first.filter(row);
	}

	public static final TextFilter transform(Filter filter, TextSchema<?> schema) {
		switch (filter.getFilterType()) {
		case EXPRESSION:
			return new TextFilterExpression((FilterExpression) filter, schema);
		case NODE:
			return new TextFilterNode((FilterNode) filter, schema);
		case TERM:
			return new TextFilterTerm((FilterTerm) filter, schema);
		default:
			throw new RuntimeException("Unknown filter type " + filter.getFilterType());
		}
	}

}
