package com.eixox.data;

// Description Here:
// _____________________________________________________
public class SortExpression {

	private final SortNode first;
	private SortNode last;

	// Description Here:
	// _____________________________________________________
	public SortExpression(ClassStorage<?> storage, int ordinal, SortDirection direction) {
		this.first = new SortNode(storage, ordinal, direction);
	}

	// Description Here:
	// _____________________________________________________
	public SortExpression(ClassStorage<?> storage, int ordinal) {
		this.first = new SortNode(storage, ordinal);
	}

	// Description Here:
	// _____________________________________________________
	public SortExpression(ClassStorage<?> storage, String name, SortDirection direction) {
		this.first = new SortNode(storage, name, direction);
	}

	// Description Here:
	// _____________________________________________________
	public SortExpression(ClassStorage<?> storage, String name) {
		this.first = new SortNode(storage, name);
	}

	// Description Here:
	// _____________________________________________________
	public final SortNode getLast() {
		return last;
	}

	// Description Here:
	// _____________________________________________________
	public final SortExpression thenBy(int ordinal, SortDirection direction) {
		this.last.setNext(new SortNode(this.first.getStorage(), ordinal, direction));
		this.last = this.last.getNext();
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final SortExpression thenBy(int ordinal) {
		this.last.setNext(new SortNode(this.first.getStorage(), ordinal));
		this.last = this.last.getNext();
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final SortExpression thenBy(String name, SortDirection direction) {
		this.last.setNext(new SortNode(this.first.getStorage(), name, direction));
		this.last = this.last.getNext();
		return this;
	}

	// Description Here:
	// _____________________________________________________
	public final SortExpression thenBy(String name) {
		this.last.setNext(new SortNode(this.first.getStorage(), name));
		this.last = this.last.getNext();
		return this;
	}
	
	// Description Here:
	// _____________________________________________________
	public final SortNode getFirst() {
		return first;
	}

}
