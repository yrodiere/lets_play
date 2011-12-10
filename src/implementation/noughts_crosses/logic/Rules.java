package implementation.noughts_crosses.logic;

import java.util.ArrayList;
import java.util.List;

import data.Coordinates.DirectionOnBoard;
import data.Player;

public class Rules extends logic.Rules {

	/*
	 * -----------------------------------------------Attributes------------------
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = -7126905238797832809L;

	public Rules(int boardSize, DirectionOnBoard hostPlayerSide ) {
		super(2, boardSize, boardSize,hostPlayerSide);
	}
	
	@Override
	public List<Player> createPlayers() {
		List<Player> players = new ArrayList<Player>();
		
		players.add(new Player(DirectionOnBoard.UP));
		
		players.add(new Player(DirectionOnBoard.DOWN));
		
		return players;
	}
}
