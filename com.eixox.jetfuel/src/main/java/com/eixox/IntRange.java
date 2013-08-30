package com.eixox;

public class IntRange {

	private final int start;
	private final int end;

	public IntRange(int start, int end) {
		this.start = start;
		this.end = end;
	}

	public String toString() {
		return "[" + start + ", " + end + "]";
	}

	/**
	 * @return the start
	 */
	public final int getStart() {
		return start;
	}

	/**
	 * @return the end
	 */
	public final int getEnd() {
		return end;
	}
	
	

}
