package implementation.draughts.logic;

import implementation.draughts.PieceType;
import data.Board;
import data.Piece;

final class ManMoveStrategy extends MoveStrategy {

	public ManMoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules);
		// TODO: handle custom rules
	}

	@Override
	public boolean select() {
		setReachablesNoCaptureTowards(FORWARD_LEFT, 1);
		setReachableWithCaptureTowards(FORWARD_LEFT, 1);
		setReachablesNoCaptureTowards(FORWARD_RIGHT, 1);
		setReachableWithCaptureTowards(FORWARD_RIGHT, 1);

		// TODO: handle custom rules
		setReachableWithCaptureTowards(BACKWARD_RIGHT, 1);
		setReachableWithCaptureTowards(BACKWARD_LEFT, 1);

		return canMove();
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.MAN.hashCode();
	}
}
