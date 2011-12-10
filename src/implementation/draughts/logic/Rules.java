package implementation.draughts.logic;

import java.util.ArrayList;
import java.util.List;

import data.Coordinates.DirectionOnBoard;
import data.Player;
import data.Tile;

public class Rules extends logic.Rules {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9019824896737042795L;
	/*
	 * -----------------------------------------------Attributes------------------
	 */
	protected int nbRowsOfPiece;

	public Rules(int boardSize, DirectionOnBoard hostPlayerSide ) {
		super(2, boardSize,boardSize,hostPlayerSide);

		nbRowsOfPiece = (boardSize - 1)/2;
	}

	public int getNbRowsOfPiece() {
		return nbRowsOfPiece;
	}

	public boolean isTileUsed(Tile tile) {
		return ((tile.getCoordinates().getRow() + tile.getCoordinates()
				.getColumn()) % 2 == 0);
	}

	@Override
	public List<Player> createPlayers() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new Player(DirectionOnBoard.UP));
		
		players.add(new Player(DirectionOnBoard.DOWN));
		
		return players;
	}
}
