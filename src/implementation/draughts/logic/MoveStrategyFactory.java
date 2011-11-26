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
		switch (PieceType.fromHash(typeHashCode)) {
		case MAN:
			return new ManMoveStrategy(controlledPiece, board, rules);
		case KING:
			return new KingMoveStrategy(controlledPiece, board, rules);
		default:
			throw new IllegalArgumentException(
					"PieceType corresponding to typeHashCode is unknown");
		}
	}

}
