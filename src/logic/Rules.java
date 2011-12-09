package logic;

import java.io.Serializable;
import java.util.List;

import data.Coordinates.DirectionOnBoard;
import data.Player;

public abstract class Rules implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7546247965028451160L;
	/*
	 * -----------------------------------------------Attributes------------------
	 */
	private int nbPlayer;
	private int boardRowCount;
	private int boardColumnCount;
	private DirectionOnBoard hostPlayerSide;

	/*
	 * -----------------------------------------------Methodes--------------------
	 */

	public Rules(int nbPlayer, int boardRowCount, int boardColumnCount, DirectionOnBoard hostPlayerSide) {
		super();
		this.nbPlayer = nbPlayer;
		this.boardRowCount = boardRowCount;
		this.boardColumnCount = boardColumnCount;
		this.hostPlayerSide = hostPlayerSide;
	}

	public int getBoardRowCount() {
		return boardRowCount;
	}

	public int getBoardColumnCount() {
		return boardColumnCount;
	}

	public int getNbPlayer() {
		return nbPlayer;
	}
	
	public DirectionOnBoard getHostPlayerSide() {
		return hostPlayerSide;
	}	
	
	abstract public List<Player> createPlayers();
}
