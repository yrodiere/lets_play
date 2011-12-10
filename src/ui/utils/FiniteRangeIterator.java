package ui.utils;

import java.util.Iterator;

public abstract class FiniteRangeIterator implements
		Iterator<Integer> {
	protected Integer current;
	protected final Integer initialValue;

	protected FiniteRangeIterator(int initialValue) {
		current = initialValue;
		this.initialValue = initialValue;
	}

	public final Integer current() {
		return current;
	}

	public final void reset() {
		current = initialValue;
	}

	@Override
	public final void remove() {
		throw new UnsupportedOperationException(
				"Cannot remove an element; this is a read-only iterator");
	}
	
	public abstract int getUpperBound();
}