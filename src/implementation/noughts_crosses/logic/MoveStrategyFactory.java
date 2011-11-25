package implementation.noughts_crosses.logic;

import implementation.noughts_crosses.PieceType;
import data.Board;
import data.Piece;

public class MoveStrategyFactory implements logic.MoveStrategyFactory {
	protected final Board board;
	protected final Rules rules;

	public MoveStrategyFactory(Board board, Rules rules) {
		this.board = board;
		this.rules = rules;
	}

	@Override
	public logic.MoveStrategy createMoveStrategy(int typeHashCode,
			Piece controlledPiece) {
		if (PieceType.MAIN.hashCode() == typeHashCode) {
			return new MoveStrategy(controlledPiece, board, rules);
		} else {
			throw new IllegalArgumentException("typeHashCode is invalid");
		}
	}

}
