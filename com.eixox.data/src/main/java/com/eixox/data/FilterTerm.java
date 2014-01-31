package com.eixox.data;

// Description Here:
// _____________________________________________________
public class FilterTerm implements Filter {

	private final ClassStorage storage;
	private final int ordinal;
	private final FilterComparison comparison;
	private final Object value;

	// Description Here:
	// _____________________________________________________
	public FilterTerm(ClassStorage storage, int ordinal, FilterComparison comparison, Object value) {
		this.storage = storage;
		this.ordinal = ordinal;
		this.comparison = comparison;
		this.value = value;
	}

	// Description Here:
	// _____________________________________________________
	public FilterTerm(ClassStorage storage, int ordinal, Object value) {
		this(storage, ordinal, FilterComparison.EqualTo, value);
	}

	// Description Here:
	// _____________________________________________________
	public FilterTerm(ClassStorage storage, String name, FilterComparison comparison, Object value) {
		this(storage, storage.getOrdinal(name), comparison, value);
	}

	// Description Here:
	// _____________________________________________________
	public FilterTerm(ClassStorage storage, String name, Object value) {
		this(storage, storage.getOrdinal(name), value);
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage getStorage() {
		return storage;
	}

	// Description Here:
	// _____________________________________________________
	public final int getOrdinal() {
		return ordinal;
	}
	
	// Description Here:
	// _____________________________________________________
	public final FilterComparison getComparison() {
		return comparison;
	}
	
	// Description Here:
	// _____________________________________________________
	public final Object getValue() {
		return value;
	}

}
