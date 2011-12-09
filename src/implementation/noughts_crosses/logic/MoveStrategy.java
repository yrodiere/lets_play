package implementation.noughts_crosses.logic;

import implementation.noughts_crosses.PieceType;
import data.Board;
import data.Coordinates;
import data.Piece;
import data.Tile;

class MoveStrategy extends logic.MoveStrategy {

	public MoveStrategy(Piece controlledPiece, Board board, Rules rules) {
		super(controlledPiece, board, rules, new MoveStrategyFactory(board,
				rules));
	}

	@Override
	public boolean select() {
		throw new UnsupportedOperationException(
				"No piece should be selected in this game");
	}

	@Override
	public boolean tryMove(Coordinates target) {
		Tile targetTile = board.findTileAt(target);
		if (!canMove() || targetTile == null || !targetTile.isEmpty()) {
			return false;
		} else {
			movePiece(controlledPiece, targetTile);
			return true;
		}
	}

	@Override
	public boolean canMove() {
		return controlledPiece.getCoordinates() == null;
	}

	@Override
	public int getTypeHashCode() {
		return PieceType.MAIN.hashCode();
	}
}
