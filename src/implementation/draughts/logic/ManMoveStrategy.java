package implementation.draughts.logic;

import implementation.draughts.PieceType;
import data.Board;
import data.Coordinates.DirectionOnBoard;
import data.Piece;

final class ManMoveStrategy extends MoveStrategy {

	public ManMoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules);
		// TODO: handle custom rules
	}

	@Override
	protected boolean scanReachables(boolean markThem) {
		boolean foundAtLeastOne = false;
		for (DirectionOnBoard direction : new DirectionOnBoard[] {
				FORWARD_LEFT, FORWARD_RIGHT }) {
			foundAtLeastOne = setReachablesNoCaptureTowards(direction, 1,
					markThem) || foundAtLeastOne;
			foundAtLeastOne = setReachableWithCaptureTowards(direction, 1,
					markThem) || foundAtLeastOne;
		}

		// TODO: handle custom rules
		foundAtLeastOne = setReachableWithCaptureTowards(BACKWARD_RIGHT, 1,
				markThem) || foundAtLeastOne;
		foundAtLeastOne = setReachableWithCaptureTowards(BACKWARD_LEFT, 1,
				markThem) || foundAtLeastOne;

		return foundAtLeastOne;
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.MAN.hashCode();
	}
}
