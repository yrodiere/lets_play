package implementation.draughts.logic;


import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Player;

//TODO
public class Game extends logic.Game {
	
	protected final int nbPlayers = 2;
	
	protected final int nbRowOfPieces = 3;

	Game(Rules myRules, Board myBoard, List<Player> myPlayers) throws Exception {
		super(myRules, myBoard, myPlayers);
		
		if(myPlayers.size() != nbPlayers || myPlayers.get(0) == null || myPlayers.get(1) == null ){
			throw new Exception("Two players required !");		
		}
		
		init();
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
		
		checkForPieceUpgrade(actor);
		
		Player otherPlayer = players.get(0);
		
		if(otherPlayer == actor){
			otherPlayer = players.get(1);
		}	
		
		List<Piece> otherPlayerPieces = otherPlayer.findOnBoardPieces();
		
		if(otherPlayerPieces.size() == 0){
			actor.win();
			otherPlayer.loose();
			return EndTurnReturnCode.SUCCESS;
		}
		
		for(Piece piece : otherPlayerPieces){
			if(piece.canMove()){
				actor.endTurn();
				otherPlayer.endTurn();
				return EndTurnReturnCode.SUCCESS;
			}
		}
		
		actor.win();
		otherPlayer.loose();
		return EndTurnReturnCode.SUCCESS;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}
	
	protected void checkForPieceUpgrade(Player actor){
		// TODO
	}

}
