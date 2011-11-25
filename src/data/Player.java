package data;

import java.io.Serializable;
import java.util.List;
import java.util.Observable;

import data.Coordinates.DirectionOnBoard;

//TODO
public final class Player extends Observable implements GameData {
	public static enum PlayerStatus {/* TODO */
		PLAYING
	};

	public static final class PlayerModification implements Serializable {
		private static final long serialVersionUID = 6290017442804625491L;
		// TODO
	}

	/**
	 * Allows other objects to identify this player without knowing the other
	 * player.
	 */
	private final DirectionOnBoard boardSide;

	public Player(DirectionOnBoard boardSide) {
		this.boardSide = boardSide;
	}

	@Override
	public void resetTurn() {
		// TODO Auto-generated method stub
		// Just call resetTurn on every owned piece and call notifyObservers
	}

	@Override
	public void endTurn() {
		// TODO Auto-generated method stub
		// Just call endTurn on every owned piece and call notifyObservers
	}

	public PlayerStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO
	}

	public List<Piece> findOnBoardPieces() {
		// TODO
		return null;
	}

	public List<Piece> findOffBoardPieces() {
		// TODO
		return null;
	}

	public void win() {
		// TODO Auto-generated method stub

	}

	public void loose() {
		// TODO Auto-generated method stub

	}

	public void draw() {
		// TODO Auto-generated method stub

	}

}
