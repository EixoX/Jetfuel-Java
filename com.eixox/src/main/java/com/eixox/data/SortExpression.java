package com.eixox.data;

public final class SortExpression {

	private final SortNode sortFirst;
	private SortNode sortLast;

	public SortExpression(DataAspect aspect, int ordinal, SortDirection direction) {
		this.sortFirst = new SortNode(aspect, ordinal, direction);
		this.sortLast = this.sortFirst;
	}

	public SortExpression(DataAspect aspect, SortDirection direction, int... ordinals) {
		this.sortFirst = new SortNode(aspect, ordinals[0], direction);
		this.sortLast = this.sortFirst;
		for (int i = 1; i < ordinals.length; i++) {
			this.sortLast.setNext(new SortNode(this.sortFirst.getAspect(), ordinals[i], direction));
			this.sortLast = this.sortLast.getNext();
		}
	}

	public SortExpression(DataAspect aspect, SortDirection direction, String... names) {
		this.sortFirst = new SortNode(aspect, names[0], direction);
		this.sortLast = this.sortFirst;
		for (int i = 1; i < names.length; i++) {
			this.sortLast.setNext(new SortNode(this.sortFirst.getAspect(), names[i], direction));
			this.sortLast = this.sortLast.getNext();
		}
	}

	public final SortExpression orderBy(SortDirection direction, String... names) {
		for (int i = 0; i < names.length; i++) {
			this.sortLast.setNext(new SortNode(this.sortFirst.getAspect(), names[i], direction));
			this.sortLast = this.sortLast.getNext();
		}
		return this;
	}

	public final SortExpression orderBy(String... names) {
		return orderBy(SortDirection.ASCENDING, names);
	}

	public final SortExpression orderBy(SortDirection direction, int... ordinals) {
		for (int i = 0; i < ordinals.length; i++) {
			this.sortLast.setNext(new SortNode(this.sortFirst.getAspect(), ordinals[i], direction));
			this.sortLast = this.sortLast.getNext();
		}
		return this;
	}

	public final SortExpression orderBy(int... ordinals) {
		return orderBy(SortDirection.ASCENDING, ordinals);
	}

	public final SortNode getNode() {
		return this.sortFirst;
	}
}
