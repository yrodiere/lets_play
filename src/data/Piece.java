package data;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import logic.MoveStrategy;

public final class Piece extends Observable implements GameData {

	private final Player owner;
	private Tile lastPosition;
	private Tile position;
	private MoveStrategy strategy;
	private boolean offBoardFlag = false;

	public static enum PieceModificationType {
		INITIALISATION, TURN_RESET, FLAGGED_OFFBOARD, MOVED_OFFBOARD, MOVED_ONBOARD, CHANGED_STRATEGY
	}

	/**
	 * Immutable, {@link Serializable} class that describes a modification that
	 * occurred on a {@link Piece}. <br>
	 * Used to notify observers of a modification in the state of an observed
	 * {@link Piece}.
	 */
	public static final class PieceModification implements Serializable {
		private static final long serialVersionUID = 4962179434952606781L;
		private final PieceModificationType modifType;
		private final Coordinates newCoord;
		private final int newTypeHashCode;

		private PieceModification(PieceModificationType modifType,
				Coordinates newCoord, int newTypeHashCode) {
			super();
			this.modifType = modifType;
			this.newCoord = newCoord;
			this.newTypeHashCode = newTypeHashCode;
		}

		public PieceModificationType getType() {
			return modifType;
		}

		public Coordinates getNewCoordinates() {
			return newCoord;
		}

		public int getNewTypeHashCode() {
			return newTypeHashCode;
		}
	}

	public Piece(Player owner) {
		this(owner, null);
	}

	public Piece(Player owner, Tile initialPosition) {
		if (owner == null) {
			throw new IllegalArgumentException(
					"A piece must have a non-null owner.");
		}
		this.owner = owner;
		lastPosition = initialPosition;
		setPosition(initialPosition);
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, new PieceModification(
				PieceModificationType.INITIALISATION, getCoordinates(),
				getTypeHashCode()));
	}

	/**
	 * @see {@link MoveStrategy#update(PieceModification)}
	 */
	public void update(PieceModification updateDescription) {
		strategy.update(updateDescription);
	}

	@Override
	public void resetTurn() {
		strategy.resetTurn();
		if (position != lastPosition) {
			setChanged();
			notifyObservers(new PieceModification(
					PieceModificationType.TURN_RESET,
					lastPosition.getCoordinates(), getTypeHashCode()));
		}
		if(position != null)
			position.setPiece(null);
		if(lastPosition != null)
			lastPosition.setPiece(this);
		position = lastPosition;
		offBoardFlag = false;
	}

	@Override
	public void endTurn() {
		strategy.endTurn();
		if (offBoardFlag) {
			setPosition(null);
		}
		lastPosition = position;
	}

	/**
	 * @see {@link MoveStrategy#select()}
	 */
	public boolean select() {
		if(strategy.select()){
			if(position != null)
				position.setSelected(true);
			
			return true;
		}
		return false;
	}

	/**
	 * @see {@link MoveStrategy#tryMove(Coordinates)}
	 */
	public boolean tryMove(Coordinates target) {
		return strategy.tryMove(target);
	}

	/**
	 * @see {@link MoveStrategy#canMove()}
	 */
	public boolean canMove() {
		return strategy.canMove();
	}

	public void setMoveStrategy(MoveStrategy newStrategy) {
		if (newStrategy != strategy) {
			setChanged();
			notifyObservers(new PieceModification(
					PieceModificationType.CHANGED_STRATEGY, getCoordinates(),
					newStrategy.getTypeHashCode()));
			strategy = newStrategy;
		}
	}

	public boolean getOffBoardFlag() {
		return offBoardFlag;
	}

	public Coordinates getCoordinates() {
		if(position == null)
			return null;
		return position.getCoordinates();
	}

	public Tile getPosition() {
		return position;
	}

	public Player getPlayer() {
		return owner;
	}

	/**
	 * @see {@link MoveStrategy#getTypeHashCode()}
	 */
	public int getTypeHashCode() {
		if(strategy == null)
			return -1;
		return strategy.getTypeHashCode();
	}

	void setPosition(Tile newPosition) {		
		if (newPosition != position) {
			if (newPosition != null) {
				setChanged();
				notifyObservers(new PieceModification(
						PieceModificationType.MOVED_ONBOARD,
						newPosition.getCoordinates(), getTypeHashCode()));
				
			} else {
				setChanged();
				notifyObservers(new PieceModification(
						PieceModificationType.MOVED_OFFBOARD, null,
						getTypeHashCode()));
			}
			
			if(position != null)
				position.setPiece(null);
			
			position = newPosition;
			
			if(position != null)
				position.setPiece(this);
		}
	}

	void setOffBoardFlag() {
		if (!offBoardFlag) {
			setChanged();			
			notifyObservers(new PieceModification(
					PieceModificationType.FLAGGED_OFFBOARD, getCoordinates(),
					getTypeHashCode()));
			offBoardFlag = true;
		}
	}
}
