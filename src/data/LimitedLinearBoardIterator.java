package data;

import data.Board;
import data.LinearBoardIterator;
import data.Coordinates.DirectionOnBoard;
import data.Tile;

/**
 * A {@link LinearBoardIterator} that can stop before the edge of the board
 * based on condition defined by derived classes.
 */
public abstract class LimitedLinearBoardIterator extends LinearBoardIterator {
	public LimitedLinearBoardIterator(Board board, Tile initialTile,
			DirectionOnBoard direction) {
		super(board, initialTile, direction);
	}

	@Override
	protected final void setNext() {
		super.setNext();
		if (nextTile != null) {
			if (mustStop()) {
				// We cannot go any further
				nextTile = null;
			}
		}
	}

	protected abstract boolean mustStop();

}
