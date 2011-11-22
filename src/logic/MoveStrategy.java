package logic;

import data.Coordinates;
import data.GameData;
import data.Piece.PieceModification;
import data.PieceMover;

//TODO
public abstract class MoveStrategy extends PieceMover implements GameData {

	public void update(PieceModification updateDescription) {
		// TODO
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resetTurn() {
		// TODO Auto-generated method stub
	}

	public abstract boolean select();

	public abstract boolean tryMove(Coordinates target);

	public abstract boolean canMove();
}
