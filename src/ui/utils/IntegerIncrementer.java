package ui.utils;

public class IntegerIncrementer extends FiniteRangeIterator {
	private final Integer excludedMax;

	public IntegerIncrementer(int initialValue, int excludedMax) {
		super(initialValue - 1);
		this.excludedMax = excludedMax;
	}

	@Override
	public final boolean hasNext() {
		return current < (excludedMax - 1);
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			current = null;
		} else {
			++current;
		}
		return current;
	}

	@Override
	public int getUpperBound() {
		return excludedMax;
	}
}