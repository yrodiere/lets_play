package implementation.draughts.logic;

import implementation.draughts.PieceType;
import logic.MoveStrategy;
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
	public MoveStrategy createMoveStrategy(int typeHashCode,
			Piece controlledPiece) {
		if (PieceType.MAN.hashCode() == typeHashCode) {
			return new ManMoveStrategy(controlledPiece, board, rules);
		} else if (PieceType.KING.hashCode() == typeHashCode) {
			return new KingMoveStrategy(controlledPiece, board, rules);
		} else {
			throw new IllegalArgumentException("typeHashCode is invalid");
		}
	}

}
