package com.eixox.data.text;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

import com.eixox.data.FilterComparison;
import com.eixox.data.FilterTerm;
import com.eixox.data.FilterType;

public class TextFilterTerm implements TextFilter {

	public final int ordinal;
	public final FilterComparison comparison;
	public final Object value;

	public TextFilterTerm(int ordinal, FilterComparison comparison, Object value) {
		this.ordinal = ordinal;
		this.comparison = comparison;
		this.value = value;
	}

	public TextFilterTerm(FilterTerm term, TextSchema<?> schema) {
		this.ordinal = schema.getOrdinal(term.name);
		this.comparison = term.comparison;
		this.value = term.value;
		if (this.ordinal < 0)
			throw new RuntimeException(term.name + " was not found on " + schema.getTableName());
	}

	public FilterType getFilterType() {
		return FilterType.TERM;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public boolean filter(Object[] row) {
		Object obj = row[ordinal];
		switch (comparison) {
		case EQUAL_TO:
			return obj == null ? value == null : obj.equals(value);
		case GREATER_OR_EQUAL:
			return obj == null ? false : ((Comparable) obj).compareTo(value) >= 0;
		case GREATER_THAN:
			return obj == null ? false : ((Comparable) obj).compareTo(value) > 0;
		case IN:
			return obj == null ? false : testIn(value, obj);
		case LIKE:
			return value == null ? false : Pattern.matches(value.toString(), obj.toString());
		case LOWER_OR_EQUAL:
			return obj == null ? false : ((Comparable) obj).compareTo(value) <= 0;
		case LOWER_THAN:
			return obj == null ? false : ((Comparable) obj).compareTo(value) < 0;
		case NOT_EQUAL_TO:
			return obj == null ? value != null : !obj.equals(value);
		case NOT_IN:
			return obj == null ? true : !testIn(value, obj);
		case NOT_LIKE:
			return value == null ? true : !Pattern.matches(value.toString(), obj.toString());
		default:
			throw new RuntimeException("Unknown filter comparison " + comparison);
		}
	}

	@SuppressWarnings("rawtypes")
	private boolean testIn(Object collection, Object value) {
		if (collection.getClass().isArray()) {
			int s = Array.getLength(collection);
			for (int i = 0; i < s; i++)
				if (value.equals(Array.get(collection, i)))
					return true;
			return false;
		} else {
			for (Object obj : (Iterable) collection) {
				if (value.equals(obj))
					return true;
			}
			return false;
		}
	}

}
