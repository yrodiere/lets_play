package implementation.noughts_crosses.logic;

import java.util.List;

import data.Board;
import data.Coordinates;
import data.Player;

//TODO
public class Game extends logic.Game {

	public Game(Rules myRules, Board myBoard, List<Player> myPlayers) {
		super(myRules, myBoard, myPlayers);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SelectionReturnCode select(Player actor, Coordinates location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetTurn(Player actor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
}
