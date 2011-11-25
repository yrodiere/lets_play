package logic;

import data.Board;
import data.Coordinates;
import data.GameData;
import data.Piece;
import data.Piece.PieceModification;
import data.PieceMover;

public abstract class MoveStrategy extends PieceMover implements GameData {
	protected final Piece controlledPiece;
	protected final Board board;
	protected final MoveStrategyFactory strategyFactory;

	public MoveStrategy(Piece controlledPiece, Board board, Rules rules,
			MoveStrategyFactory strategyFactory) {
		this.controlledPiece = controlledPiece;
		this.board = board;
		this.strategyFactory = strategyFactory;
	}

	public void update(PieceModification updateDescription) {
		switch (updateDescription.getType()) {
		case MOVED_OFFBOARD:
			movePiece(controlledPiece, null);
			break;
		case MOVED_ONBOARD:
			movePiece(controlledPiece,
					board.findTileAt(updateDescription.getNewCoordinates()));
			break;
		case TURN_RESET:
			controlledPiece.resetTurn();
			break;
		case CHANGED_STRATEGY:
			controlledPiece.setMoveStrategy(strategyFactory.createMoveStrategy(
					updateDescription.getNewTypeHashCode(), controlledPiece));
			break;

		case FLAGGED_OFFBOARD:
		case INITIALISATION:
			// Should not happen; these modifications are not to be
			// synchronised.
			throw new IllegalArgumentException(
					"INITIALISATION and FLAGGED_OFFBOARD modifications are not supported.");
		}
	}

	@Override
	public void endTurn() {
		if (controlledPiece.getOffBoardFlag()) {
			movePiece(controlledPiece, null);
		}
	}

	@Override
	public void resetTurn() {
		// Do nothing by default
	}

	/**
	 * Performs the necessary actions when a piece is selected, e.g. marks the
	 * reachable tiles as such.
	 * 
	 * @return True if the selected piece can now move, false otherwise.
	 */
	public abstract boolean select();

	/**
	 * Checks if the piece can be moved, and if so, move it.
	 * 
	 * @param target
	 *            The tile to reach. Must be non-null.
	 * @return True if the move has been performed, false if it could not be.
	 */
	public abstract boolean tryMove(Coordinates target);

	/**
	 * Checks if the piece can be moved.
	 * 
	 * @return True if the piece can be moved, false otherwise.
	 */
	public abstract boolean canMove();

	/**
	 * Returns an identifier of this type.
	 * 
	 * @return The hashcode that must be given in parameter to the corresponding
	 *         {@link MoveStrategyFactory} in order for it to return a
	 *         MoveStrategy of the same type.
	 */
	public abstract int getTypeHashCode();
}
