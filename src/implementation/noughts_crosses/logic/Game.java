package implementation.noughts_crosses.logic;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Coordinates.DirectionOnBoard;
import data.Player;
import data.Tile;

public class Game extends logic.Game {

	/*
	 * -----------------------------------------------Attributes------------------
	 */

	List<List<Tile>> everyPossibleLine = new ArrayList<List<Tile>>();

	/*
	 * -----------------------------------------------Methodes--------------------
	 */

	public Game(Rules myRules, Board myBoard, List<Player> myPlayers)
			throws Exception {
		super(myRules, myBoard, myPlayers);

		init();
	}

	@Override
	protected SelectionReturnCode specificSelect(Player actor,
			Coordinates location) {
		Tile selectedTile = board.findTileAt(location);

		if (selectedPiece.tryMove(selectedTile.getCoordinates())) {
			currentPlayerHasPlayed = true;
			return SelectionReturnCode.SUCCESS;
		} else {
			return SelectionReturnCode.INVALID_TARGET_SELECTION;
		}
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {

		Player otherPlayer = players.get(0);

		if (otherPlayer == actor) {
			otherPlayer = players.get(1);
		}

		selectedPiece = otherPlayer.findOffBoardPieces().get(0);

		boolean boardFull = true;

		for (List<Tile> line : everyPossibleLine) {

			int nbActorPiecesAligned = 0;

			for (Tile tile : line) {
				
				Piece piece = tile.getPiece();

				if (piece == null) {
					boardFull = false;
				} else {
					if (piece.getPlayer() == actor) {
						nbActorPiecesAligned++;
					}
				}
			}

			if (nbActorPiecesAligned == rules.getBoardColumnCount()) {
				actor.win();
				otherPlayer.loose();
				return EndTurnReturnCode.SUCCESS;
			}
		}

		if (boardFull) {
			actor.draw();
			otherPlayer.draw();
		} else {
			actor.endTurn();
			otherPlayer.endTurn();
		}

		return EndTurnReturnCode.SUCCESS;
	}

	@Override
	protected void init() {
		Rules myRules = (Rules) rules;
		
		Player player1, player2;
		
		if (players.get(0).getBoardSide() == DirectionOnBoard.DOWN) {
			player1 = players.get(0);
			player2 = players.get(1);
		} else {
			player1 = players.get(1);
			player2 = players.get(0);
		}

		int nbTiles = myRules.getBoardRowCount()
				* myRules.getBoardColumnCount();

		List<Piece> player1Pieces = new ArrayList<Piece>();

		List<Piece> player2Pieces = new ArrayList<Piece>();

		for (int i = 0; i < (nbTiles/2 + 2); i++) {
				Piece piece1 = initPiece(player1);
				player1Pieces.add(piece1);

				Piece piece2 = initPiece(player2);
				player2Pieces.add(piece2);
		}		
		
		player1.init(player1Pieces);
		player2.init(player2Pieces);

		selectedPiece = player2Pieces.get(0);

		for (int r = 0; r < myRules.getBoardRowCount(); r++) {

			List<Tile> rowLine = new ArrayList<Tile>();
			List<Tile> columnLine = new ArrayList<Tile>();

			for (int c = 0; c < myRules.getBoardColumnCount(); c++) {
				rowLine.add(board.findTileAt(new Coordinates(r, c)));
				columnLine.add(board.findTileAt(new Coordinates(c, r)));
			}

			everyPossibleLine.add(rowLine);
			everyPossibleLine.add(columnLine);
		}

		List<Tile> diagLigne1 = new ArrayList<Tile>();
		List<Tile> diagLigne2 = new ArrayList<Tile>();

		Coordinates coords1 = new Coordinates(0, 0);
		Coordinates coords2 = new Coordinates(0,
				myRules.getBoardColumnCount() - 1);

		for (int r = 0; r < myRules.getBoardRowCount(); r++) {
			diagLigne1.add(board.findTileAt(coords1));
			diagLigne2.add(board.findTileAt(coords2));
			coords1 = coords1.translationTowards(DirectionOnBoard.UP_RIGHT);
			coords2 = coords2.translationTowards(DirectionOnBoard.UP_LEFT);
		}

		everyPossibleLine.add(diagLigne1);
		everyPossibleLine.add(diagLigne2);
	}

	protected Piece initPiece(Player owner) {

		Piece piece = new Piece(owner);

		MoveStrategy ms = new MoveStrategy(piece, board, (Rules) rules);

		piece.setMoveStrategy(ms);

		return piece;
	}
}
