package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import data.Coordinates.DirectionOnBoard;

public final class Player extends Observable implements GameData {
	public static enum PlayerStatus {
		PLAYING, WAITING, LOSS, VICTORY, DRAW;
	};

	public static enum PlayerModificationType {
		INITIALISATION, TURN_RESET, STATUS_CHANGE
	}

	public static final class PlayerModification implements Serializable {

		private static final long serialVersionUID = 6290017442804625491L;
		private final PlayerModificationType modifType;
		private final PlayerStatus newStatus;

		private PlayerModification(PlayerModificationType modifType,
				PlayerStatus newStatus) {
			super();
			this.modifType = modifType;
			this.newStatus = newStatus;
		}

		public PlayerModificationType getType() {
			return modifType;
		}

		public PlayerStatus getNewStatus() {
			return newStatus;
		}
	}

	/*
	 * ------------------------Attributs
	 */

	/**
	 * Allows other objects to identify this player without knowing the other
	 * player.
	 */
	private final DirectionOnBoard boardSide;
	/**
	 * Player pieces
	 */
	private List<Piece> pieces;
	/**
	 * Player pieces
	 */
	private PlayerStatus status;

	/*
	 * ------------------------Methods
	 */

	public Player(DirectionOnBoard boardSide) {
		this.boardSide = boardSide;
		this.pieces = new ArrayList<Piece>();
		this.status = PlayerStatus.WAITING;
	}

	public void addPieceObserver(Observer observer) {
		for (Piece piece : pieces) {
			piece.addObserver(observer);
		}
	}

	@Override
	public void resetTurn() {
		for (Piece piece : pieces) {
			piece.resetTurn();
		}

		notifyObservers(new PlayerModification(
				PlayerModificationType.TURN_RESET, this.status));
	}

	@Override
	public void endTurn() {
		for (Piece piece : pieces) {
			piece.endTurn();
		}

		PlayerStatus newStatus;

		if (this.status == PlayerStatus.PLAYING) {
			newStatus = PlayerStatus.WAITING;
		} else {
			newStatus = PlayerStatus.PLAYING;
		}

		notifyObservers(new PlayerModification(
				PlayerModificationType.STATUS_CHANGE, newStatus));

		this.status = newStatus;
	}

	public PlayerStatus getStatus() {
		return status;
	}

	/**
	 * Returns the position of the player relatively to the board.<br>
	 * For instance, in a chess game, one player will return "UP" and the other
	 * will return "DOWN".
	 * 
	 * @return The side of the board where the player stands.
	 */
	public DirectionOnBoard getBoardSide() {
		return boardSide;
	}

	public void init(List<Piece> ownedPieces) {
		pieces = ownedPieces;
	}

	public List<Piece> findOnBoardPieces() {

		List<Piece> onBoardPieces = new ArrayList<Piece>();

		for (Piece piece : pieces) {
			if (piece.getPosition() != null) {
				onBoardPieces.add(piece);
			}
		}
		return onBoardPieces;
	}

	public List<Piece> findOffBoardPieces() {

		List<Piece> offBoardPieces = new ArrayList<Piece>();

		for (Piece piece : pieces) {
			if (piece.getPosition() == null) {
				offBoardPieces.add(piece);
			}
		}
		return offBoardPieces;
	}

	public void win() {
		PlayerStatus newStatus = PlayerStatus.VICTORY;
		notifyObservers(new PlayerModification(
				PlayerModificationType.STATUS_CHANGE, newStatus));
		this.status = newStatus;
	}

	public void loose() {
		PlayerStatus newStatus = PlayerStatus.LOSS;
		notifyObservers(new PlayerModification(
				PlayerModificationType.STATUS_CHANGE, newStatus));
		this.status = newStatus;
	}

	public void draw() {
		PlayerStatus newStatus = PlayerStatus.DRAW;
		notifyObservers(new PlayerModification(
				PlayerModificationType.STATUS_CHANGE, newStatus));
		this.status = newStatus;
	}

	public void update(PlayerModification modif) {
		switch (modif.getType()) {
		case STATUS_CHANGE:
			this.status = modif.getNewStatus();
			break;
		case TURN_RESET:
			//Nothing happens
			break;

		case INITIALISATION:
			// Should not happen; these modifications are not to be
			// synchronised.
			throw new IllegalArgumentException(
					"INITIALISATION and FLAGGED_OFFBOARD modifications are not supported.");
		}		
	}

}
