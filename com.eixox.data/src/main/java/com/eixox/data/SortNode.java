package com.eixox.data;

// Description Here:
// _____________________________________________________
public class SortNode {

	private final ClassStorage<?> storage;
	private final int ordinal;
	private final SortDirection direction;
	private SortNode next;

	// Description Here:
	// _____________________________________________________
	public SortNode(ClassStorage<?> storage, int ordinal, SortDirection direction) {
		this.storage = storage;
		this.ordinal = ordinal;
		this.direction = direction;
	}

	// Description Here:
	// _____________________________________________________
	public SortNode(ClassStorage<?> storage, int ordinal) {
		this(storage, ordinal, SortDirection.Ascending);
	}

	// Description Here:
	// _____________________________________________________
	public SortNode(ClassStorage<?> storage, String name, SortDirection direction) {
		this(storage, storage.getOrdinal(name), direction);
	}

	// Description Here:
	// _____________________________________________________
	public SortNode(ClassStorage<?> storage, String name) {
		this(storage, storage.getOrdinal(name));
	}

	// Description Here:
	// _____________________________________________________
	public final SortNode getNext() {
		return next;
	}

	// Description Here:
	// _____________________________________________________
	public final void setNext(SortNode next) {
		this.next = next;
	}

	// Description Here:
	// _____________________________________________________
	public final ClassStorage<?> getStorage() {
		return storage;
	}

	// Description Here:
	// _____________________________________________________
	public final int getOrdinal() {
		return ordinal;
	}

	// Description Here:
	// _____________________________________________________
	public final SortDirection getDirection() {
		return direction;
	}

}
