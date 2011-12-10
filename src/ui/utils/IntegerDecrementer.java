package ui.utils;

public class IntegerDecrementer extends FiniteRangeIterator {
	private final Integer includedMin;

	public IntegerDecrementer(int initialValuePlusOne, int includedMin) {
		super(initialValuePlusOne);
		this.includedMin = includedMin;
	}

	@Override
	public final boolean hasNext() {
		return current > includedMin;
	}

	@Override
	public Integer next() {
		if (!hasNext()) {
			current = null;
		} else {
			--current;
		}
		return current;
	}

	@Override
	public int getUpperBound() {
		return initialValue;
	}
}