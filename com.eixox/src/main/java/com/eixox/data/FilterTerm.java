package com.eixox.data;

import java.lang.reflect.Array;
import java.util.List;
import java.util.regex.Pattern;

public class FilterTerm implements Filter {

	public String name;
	public FilterComparison comparison;
	public Object value;

	public FilterTerm(String name, FilterComparison comparison, Object value) {
		this.name = name;
		this.comparison = comparison;
		this.value = value;
	}

	public FilterTerm(String name, Object value) {
		this(name, FilterComparison.EQUAL_TO, value);
	}

	public final FilterType getFilterType() {
		return FilterType.TERM;
	}

	@SuppressWarnings("rawtypes")
	private boolean contains(Object haystack, Object needle) {
		if (haystack == null)
			return false;

		Class<? extends Object> claz = haystack.getClass();
		if (claz.isArray()) {
			int s = Array.getLength(haystack);
			for (int i = 0; i < s; i++) {
				Object o = Array.get(haystack, i);
				if (o == null) {
					if (needle == null)
						return true;
				} else if (o.equals(needle))
					return true;
			}
		} else if (Iterable.class.isAssignableFrom(claz)) {
			for (Object o : (Iterable) haystack) {
				if (o == null) {
					if (needle == null)
						return true;
				} else if (o.equals(needle))
					return true;
			}
		}
		return false;

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final boolean evaluate(List<String> cols, Object[] row) {
		int ordinal = cols.indexOf(this.name);
		if (ordinal < 0)
			throw new RuntimeException(this.name + " was not found on column list");

		Object val = row[ordinal];

		switch (this.comparison) {
		case EQUAL_TO:
			return this.value == null ? val == null : this.value.equals(val);
		case GREATER_OR_EQUAL:
			return val == null ? false : ((Comparable) val).compareTo(this.value) >= 0;
		case GREATER_THAN:
			return val == null ? false : ((Comparable) val).compareTo(this.value) > 0;
		case IN:
			return contains(this.value, val);
		case LIKE:
			return val == null ? false : Pattern.matches((String) this.value, (CharSequence) val);
		case LOWER_OR_EQUAL:
			return val == null ? false : ((Comparable) val).compareTo(this.value) <= 0;
		case LOWER_THAN:
			return val == null ? false : ((Comparable) val).compareTo(this.value) < 0;
		case NOT_EQUAL_TO:
			return this.value == null ? val != null : !this.value.equals(val);
		case NOT_IN:
			return !contains(this.value, val);
		case NOT_LIKE:
			return val == null ? true : !Pattern.matches((String) this.value, (CharSequence) val);
		default:
			throw new RuntimeException("Unknown filter comparison: " + this.comparison);

		}

	}
}
