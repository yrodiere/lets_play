package ui.utils;

import java.util.Iterator;

import data.Coordinates;
import data.Coordinates.DirectionOnBoard;

/**
 * Allows the board view to create its tile views one by one, taking the
 * orientation of the board into account.
 * 
 */
public class BoardBuildHelper implements Iterator<Coordinates> {
	private final FiniteRangeIterator rowIterator;
	private final FiniteRangeIterator colIterator;
	private final FiniteRangeIterator majorIterator;
	private final FiniteRangeIterator minorIterator;

	public BoardBuildHelper(DirectionOnBoard bottomSide, int nbRow, int nbCol) {
		switch (bottomSide) {
		case DOWN:
			rowIterator = new IntegerDecrementer(nbRow, 0);
			colIterator = new IntegerIncrementer(0, nbCol);
			majorIterator = rowIterator;
			minorIterator = colIterator;
			break;
		case UP:
			rowIterator = new IntegerIncrementer(0, nbRow);
			colIterator = new IntegerDecrementer(nbCol, 0);
			majorIterator = rowIterator;
			minorIterator = colIterator;
			break;
		case LEFT:
			rowIterator = new IntegerDecrementer(nbRow, 0);
			colIterator = new IntegerDecrementer(nbCol, 0);
			majorIterator = colIterator;
			minorIterator = rowIterator;
			break;
		case RIGHT:
			rowIterator = new IntegerIncrementer(0, nbRow);
			colIterator = new IntegerIncrementer(0, nbCol);
			majorIterator = colIterator;
			minorIterator = rowIterator;
			break;
		case DOWN_LEFT:
		case DOWN_RIGHT:
		case UP_LEFT:
		case UP_RIGHT:
		default:
			throw new IllegalArgumentException("Invalid board orientation");
		}

		majorIterator.next();
	}

	@Override
	public boolean hasNext() {
		return majorIterator.hasNext() || minorIterator.hasNext();
	}

	@Override
	public Coordinates next() {
		if (!hasNext()) {
			return null;
		}

		if (!minorIterator.hasNext()) {
			minorIterator.reset();
			majorIterator.next();
		}

		minorIterator.next();

		return new Coordinates(rowIterator.current(), colIterator.current());
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException(
				"Cannot remove an element; this is a read-only iterator");
	}

	public int getRotatedNbRow() {
		return majorIterator.getUpperBound();
	}

	public int getRotatedNbColumn() {
		return minorIterator.getUpperBound();
	}
}