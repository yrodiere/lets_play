package data;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public final class Tile extends Observable implements GameData {
	/*
	 * -----------------------------------------------Attributes------------------
	 */
	protected final Coordinates coordinates;
	protected Piece piece;
	protected boolean reachable = false;
	protected boolean selected = false;
	/*
	 * -----------------------------------------------Methodes--------------------
	 */

	public static enum TileModificationType {
		INITIALISATION, TURN_RESET, FLAGS_CHANGED, PIECE_CHANGED;
	}

	/**
	 * Immutable, {@link Serializable} class that describes a modification that
	 * occurred on a {@link Tile}. <br>
	 * Used to notify observers of a modification in the state of an observed
	 * {@link Tile}.
	 */
	public static final class TileModification implements Serializable {
		private static final long serialVersionUID = -974482670427287450L;
		private final TileModificationType type;
		private final Piece newPiece;
		private final boolean newReachableFlag;
		private final boolean newSelectedFlag;

		private TileModification(TileModificationType type, Piece newPiece,
				boolean newReachableFlag, boolean newSelectedFlag) {
			super();
			this.type = type;
			this.newPiece = newPiece;
			this.newReachableFlag = newReachableFlag;
			this.newSelectedFlag = newSelectedFlag;
		}

		public TileModificationType getType() {
			return type;
		}

		public boolean isNowReachable() {
			return newReachableFlag;
		}

		public boolean isNowSelected() {
			return newSelectedFlag;
		}

		public Piece getNewPiece() {
			return newPiece;
		}
	}

	public Tile(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, new TileModification(
				TileModificationType.INITIALISATION, this.piece,
				this.reachable, this.selected));
	}

	/**
	 * Applies the modifications described by <code>updateDescription</code>
	 * 
	 * @param updateDescription
	 *            The description of the modification
	 */
	public void update(TileModification updateDescription) {
		if (updateDescription.isNowReachable() == reachable
				|| updateDescription.isNowSelected() == selected) {
			return;
		}
		
		setChanged();
		notifyObservers(updateDescription);

		switch (updateDescription.getType()) {
		case FLAGS_CHANGED:
		case INITIALISATION:
		case TURN_RESET:
			this.reachable = updateDescription.isNowReachable();
			this.selected = updateDescription.isNowSelected();
			break;
		case PIECE_CHANGED:
			// Do nothing, the piece handles this type of modification
			// In fact, this should not happen
			throw new IllegalArgumentException(
					"PIECE_CHANGED modifications are not supported.");
		}
	}

	@Override
	public void resetTurn() {
		if (reachable || selected) {
			setChanged();
			notifyObservers(new TileModification(
					TileModificationType.TURN_RESET, this.piece, false, false));
			selected = false;
			reachable = false;
		}
	}

	@Override
	public void endTurn() {
		if (reachable || selected) {
			setChanged();
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, this.piece, false,
					false));
			selected = false;
			reachable = false;
		}
	}

	/**
	 * @return The coordinates of this tile on the {@link Board}.
	 */
	public Coordinates getCoordinates() {
		return coordinates;
	}

	/**
	 * @return The {@link Piece} currently on this tile, or <code>null</code> if
	 *         there isn't any.
	 */
	public Piece getPiece() {
		return piece;
	}

	/**
	 * @return If the currently selected {@link Piece} can reach this tile,
	 *         returns <code>true</code>. If there is no currently selected
	 *         {@link Piece} or if it cannot reach this tile, returns
	 *         <code>false</code>
	 */
	public boolean isReachable() {
		return reachable;
	}

	/**
	 * @return If this tile is currently selected (is on the path of the
	 *         selected {@link Piece}), returns <code>true</code>. If there is
	 *         no currently selected {@link Piece} or if this tile is not
	 *         selected, returns <code>false</code>
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @return If there is currently a {@link Piece} on this tile, returns
	 *         <code>true</code>. Otherwise, returns <code>false</code>
	 */
	public boolean isEmpty() {
		return piece == null;
	}

	void setPiece(Piece newPiece) {
		assert (isEmpty());
		if (newPiece != piece) {
			setChanged();
			notifyObservers(new TileModification(
					TileModificationType.PIECE_CHANGED, newPiece, reachable,
					selected));
		}
		piece = newPiece;
	}

	void setSelected(boolean selected) {
		if (this.selected != selected) {
			setChanged();
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, this.piece,
					this.reachable, selected));
			this.selected = selected;
		}
	}

	void setReachable(boolean reachable) {
		if (this.reachable != reachable) {
			setChanged();
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, this.piece, reachable,
					this.selected));
			this.reachable = reachable;
		}
	}
}
