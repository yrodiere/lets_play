package logic;

import data.Piece;

public interface MoveStrategyFactory {
	/**
	 * Creates a {@link MoveStrategy}.<br>
	 * <br>
	 * The strategy is not set on the controlled piece (no call to
	 * {@link Piece#setMoveStrategy(MoveStrategy)})
	 * 
	 * @param typeHashCode
	 *            The hashcode of the identifier of a type of piece for the
	 *            running game. (For example, in Draughts, a hashcode of a value
	 *            among those defined in
	 *            {@link implementation.draughts.PieceType})
	 * @param controlledPiece
	 *            The piece to be controlled by the strategy.
	 * @return The created MoveStrategy.
	 * 
	 * @throws IllegalArgumentException
	 *             When <code>typeHashCode</code> is invalid for the running
	 *             game.
	 */
	public MoveStrategy createMoveStrategy(int typeHashCode,
			Piece controlledPiece) throws IllegalArgumentException;
}
