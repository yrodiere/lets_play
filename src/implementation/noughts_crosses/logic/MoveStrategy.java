package implementation.noughts_crosses.logic;

import implementation.noughts_crosses.PieceType;
import data.Board;
import data.Coordinates;
import data.Piece;

//TODO
class MoveStrategy extends logic.MoveStrategy {

	public MoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules, new MoveStrategyFactory(board,
				rules));
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean select() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryMove(Coordinates target) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canMove() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.MAIN.hashCode();
	}

}
