package data;

import java.util.Observable;

import logic.MoveStrategy;

//TODO
public final class Piece extends Observable implements GameData {

	private MoveStrategy strategy;

	public static enum PieceModificationType {
		INITIALISATION, TURN_RESET, FLAGGED_OFFBOARD, MOVED_OFFBOARD, MOVED_ONBOARD
	}

	public static final class PieceModification {
		public PieceModificationType getType() {
			// TODO
			return null;
		}

		public Coordinates getNewCoordinates() {
			// TODO
			return null;
		}
	}

	public Piece(Tile tile) {
		// TODO Auto-generated constructor stub
	}

	public void update(PieceModification updateDescription) {
		// TODO
	}

	@Override
	public void resetTurn() {
		strategy.resetTurn();
	}

	@Override
	public void endTurn() {
		strategy.endTurn();
	}

	public boolean select() {
		return strategy.select();
	}

	public boolean tryMove(Coordinates target) {
		return strategy.tryMove(target);
	}

	public boolean canMove() {
		return strategy.canMove();
	}

	public void setMoveStrategy(MoveStrategy strategy) {

	}

	public boolean getOffBoardFlag() {
		// TODO
		return false;
	}

	public Coordinates getCoordinates() {
		// TODO
		return null;
	}

	public Tile getPosition() {
		// TODO
		return null;
	}
	
	public Player getPlayer() {
		// TODO
		return null;
	}

	void setPosition(Tile newPosition) {
		if (newPosition == null) {
			throw new IllegalArgumentException("newPosition must be non-null");
		}
		// TODO
	}

	void setOffBoardFlag(boolean flagValue) {
		// TODO
	}
}
