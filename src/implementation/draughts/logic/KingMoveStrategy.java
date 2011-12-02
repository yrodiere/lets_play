package implementation.draughts.logic;

import implementation.draughts.PieceType;
import data.Board;
import data.Piece;
import data.Coordinates.DirectionOnBoard;

final class KingMoveStrategy extends MoveStrategy {

	public KingMoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules);
		// TODO: handle custom rules
	}

	@Override
	public boolean select() {
		// TODO: handle custom rules
		for (DirectionOnBoard direction : new DirectionOnBoard[] {
				FORWARD_RIGHT, BACKWARD_RIGHT, BACKWARD_LEFT, FORWARD_LEFT }) {
			setReachablesNoCaptureTowards(direction, Integer.MAX_VALUE);
			setReachableWithCaptureTowards(direction, Integer.MAX_VALUE);
		}

		return canMove();
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.KING.hashCode();
	}
}
