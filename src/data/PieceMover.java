package data;

/**
 * Write accessor class, allowing to show {@link Piece} and {@link Tile}
 * "setters" only to those who need them.<br>
 * For example, the UI will not be able to use these setters.
 * 
 */
public abstract class PieceMover {
	protected static void movePiece(Piece piece, Tile newPosition) {
		piece.setPosition(newPosition);
	}

	protected static void setOffBoardFlag(Piece piece) {
		piece.setOffBoardFlag();
	}

	protected static void setSelected(Tile tile, boolean selected) {
		tile.setSelected(selected);
	}

	protected static void setReachable(Tile tile, boolean reachable) {
		tile.setReachable(reachable);
	}
}
