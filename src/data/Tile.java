package data;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public final class Tile extends Observable implements GameData {
	/*
	 * -----------------------------------------------Attributes------------------
	 * ------------------------------------------
	 */
	protected final Coordinates coordinates;
	protected Piece piece;
	protected boolean reachable = false;
	protected boolean selected = false;

	/*
	 * -----------------------------------------------Methodes--------------------
	 * ----------------------------------------
	 */

	public static enum TileModificationType {
		INITIALISATION, TURN_RESET, FLAGS_CHANGED;
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
		private final boolean newReachableFlag;
		private final boolean newSelectedFlag;

		private TileModification(TileModificationType type,
				boolean newReachableFlag, boolean newSelectedFlag) {
			super();
			this.type = type;
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
	}

	public Tile(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public synchronized void addObserver(Observer o) {
		super.addObserver(o);
		o.update(this, new TileModification(
				TileModificationType.INITIALISATION, this.reachable,
				this.selected));
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

		notifyObservers(updateDescription);

		switch (updateDescription.getType()) {
		case FLAGS_CHANGED:
		case INITIALISATION:
		case TURN_RESET:
			this.reachable = updateDescription.isNowReachable();
			this.selected = updateDescription.isNowSelected();
			break;
		}
	}

	@Override
	public void resetTurn() {
		if (reachable || selected) {
			notifyObservers(new TileModification(
					TileModificationType.TURN_RESET, false, false));
			selected = false;
			reachable = false;
		}
	}

	@Override
	public void endTurn() {
		if (reachable || selected) {
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, false, false));
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
		assert (piece != null);
		piece = newPiece;
	}

	void setSelected(boolean selected) {
		if (this.selected != selected) {
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, this.reachable,
					selected));
			this.selected = selected;
		}
	}

	void setReachable(boolean reachable) {
		if (this.reachable != reachable) {
			notifyObservers(new TileModification(
					TileModificationType.FLAGS_CHANGED, reachable,
					this.selected));
			this.reachable = reachable;
		}
	}
}
