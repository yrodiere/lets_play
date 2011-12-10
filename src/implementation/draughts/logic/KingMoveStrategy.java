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
	protected boolean scanReachables(boolean markThem) {
		boolean foundAtLeastOne = false;
		// TODO: handle custom rules
		for (DirectionOnBoard direction : new DirectionOnBoard[] {
				FORWARD_RIGHT, BACKWARD_RIGHT, BACKWARD_LEFT, FORWARD_LEFT }) {
			foundAtLeastOne = setReachablesNoCaptureTowards(direction, Integer.MAX_VALUE, markThem)|| foundAtLeastOne;
			foundAtLeastOne = setReachableWithCaptureTowards(direction, Integer.MAX_VALUE, markThem)|| foundAtLeastOne;
		}

		return foundAtLeastOne;
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.KING.hashCode();
	}
}
