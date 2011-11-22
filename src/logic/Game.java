package logic;

import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Player.PlayerStatus;

//TODO
public abstract class Game {
	
	public enum SelectionReturnCode {
		SUCCESS,
		MOVE_PENDING,
		NOT_PLAYING,
		INVALID_PIECE_SELECTION,
		INVALID_TARGET_SLECTION;
	}
	
	public enum EndTurnReturnCode {
		SUCCESS,	
		NOT_PLAYING,
		MUST_PLAY_BEFORE_ENDTURN,
		INVALID_MOVE;	
	}
	
	/*
	 *  -----------------------------------------------Attributes------------------------------------------------------------
	 */
	protected boolean currentPlayerHasPlayed = false;
	
	protected Rules rules;
	
	protected Board board;
	
	protected List<Player> players;
	
	protected Piece selectedPiece = null;
	
	
	/*
	 *  -----------------------------------------------Methodes------------------------------------------------------------
	 */
	
	public Game(Rules myRules, Board myBoard, List<Player> myPlayers){
		
		rules = myRules;

		board = myBoard;
		
		players = myPlayers;	
	}
	
	/**
	 *  
	 */
	abstract public SelectionReturnCode select(Player actor, Coordinates location);
	
	/**
	 *  
	 */
	abstract public void resetTurn(Player actor);
	
	/**
	 *  
	 */
	public EndTurnReturnCode endTurn(Player actor){
		
		if(actor.getStatus() != PlayerStatus.PLAYING){
			return EndTurnReturnCode.NOT_PLAYING;
		}
		
		if(!currentPlayerHasPlayed){
			return EndTurnReturnCode.MUST_PLAY_BEFORE_ENDTURN;
		}
		
		return specificEndTurn(actor);
	}
	
	abstract protected void init();	
	
	abstract protected EndTurnReturnCode specificEndTurn(Player actor);
}
