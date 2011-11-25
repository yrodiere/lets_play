package data;

import java.util.Iterator;
import java.util.NoSuchElementException;

import data.Coordinates.DirectionOnBoard;


public class LinearBoardIterator implements Iterator<Tile> {
	private final Board board;
	private final DirectionOnBoard direction;
	protected Tile lastTile;
	protected Tile nextTile;

	/**
	 * Constructs an {@link Iterator} that will iterate through the tiles on
	 * <code>board</code> in the given <code>direction</code> relatively to
	 * <code>initialTile</code>, up to the edge of the board.
	 * 
	 * @param board
	 *            The board on which to "travel"
	 * @param initialTile
	 *            The tile from which to start (excluded from the path)
	 * @param direction
	 *            The direction towards which to "travel"
	 */
	public LinearBoardIterator(Board board, Tile initialTile,
			DirectionOnBoard direction) {
		if (board == null || initialTile == null || direction == null) {
			throw new IllegalArgumentException(
					"board, initialTile and direction must be non-null");
		}

		this.board = board;
		this.lastTile = initialTile;
		this.direction = direction;
		setNext();
	}

	protected void setNext() {
		Coordinates nextCoord = lastTile.getCoordinates().translationTowards(
				direction);
		nextTile = board.findTileAt(nextCoord);
	}

	@Override
	public final boolean hasNext() {
		return nextTile != null;
	}

	@Override
	public final Tile next() {
		if (!hasNext()) {
			throw new NoSuchElementException("End of board was reached.");
		}

		setNext();
		return lastTile;
	}

	@Override
	public final void remove() {
		throw new UnsupportedOperationException(
				"Cannot remove tiles on a board");
	}

}
