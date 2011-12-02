package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Player.PlayerStatus;

public abstract class Game {

	public enum SelectionReturnCode {
		SUCCESS, MOVE_PENDING, NOT_PLAYING, INVALID_PIECE_SELECTION, INVALID_TARGET_SELECTION;
	}

	public enum EndTurnReturnCode {
		SUCCESS, NOT_PLAYING, MUST_PLAY_BEFORE_ENDTURN, INVALID_MOVE;
	}

	/*
	 * -----------------------------------------------Attributes------------------
	 * ------------------------------------------
	 */

	protected boolean currentPlayerHasPlayed = false;

	protected final Rules rules;

	protected final Board board;

	protected final List<Player> players;

	protected Piece selectedPiece = null;

	/*
	 * -----------------------------------------------Methodes--------------------
	 * ----------------------------------------
	 */

	public Game(Rules myRules, Board myBoard, List<Player> myPlayers)
			throws Exception {

		rules = myRules;

		board = myBoard;

		players = new ArrayList<Player>(myPlayers);

		if (myPlayers.size() != rules.getNbPlayer() || myPlayers.contains(null)) {
			throw new Exception(rules.getNbPlayer() + " players required !");
		}
	}

	/**
	 * @param actor
	 *            Player who led the selection attempt.
	 * @param location
	 *            Coordinates where the player acted on.
	 * @return code specifying the result of the select attempt.
	 */
	public SelectionReturnCode select(Player actor, Coordinates location) {

		if (actor.getStatus() != PlayerStatus.PLAYING) {
			return SelectionReturnCode.NOT_PLAYING;
		}

		return specificSelect(actor, location);
	}

	/**
	 * @param actor
	 *            Player who led the end of the turn attempt.
	 * @return code specifying the result of the end of the turn attempt.
	 */
	public EndTurnReturnCode endTurn(Player actor) {

		if (actor.getStatus() != PlayerStatus.PLAYING) {
			return EndTurnReturnCode.NOT_PLAYING;
		}

		if (!currentPlayerHasPlayed) {
			return EndTurnReturnCode.MUST_PLAY_BEFORE_ENDTURN;
		}

		return specificEndTurn(actor);
	}
	
	public void addObservers(Observer obs){
		
		for(Player player : players){
			player.addObserver(obs);
			player.addPieceObserver(obs);
		}
		
		board.addTileObserver(obs);
	}

	abstract protected void init();

	abstract protected EndTurnReturnCode specificEndTurn(Player actor);

	abstract protected SelectionReturnCode specificSelect(Player actor,
			Coordinates location);
}
