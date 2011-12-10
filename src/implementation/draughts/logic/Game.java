package implementation.draughts.logic;

import java.util.ArrayList;
import java.util.List;

import data.Board;
import data.Coordinates;
import data.Piece;
import data.Player;
import data.Tile;
import data.Coordinates.DirectionOnBoard;

public class Game extends logic.Game {
	public Game(Rules myRules, Board myBoard, List<Player> myPlayers) throws Exception {
		super(myRules, myBoard, myPlayers);

		init();
	}

	@Override
	protected SelectionReturnCode specificSelect(Player actor,
			Coordinates location) {

		Tile selectedTile = board.findTileAt(location);

		if (selectedTile == null) {
			return null;
		}

		Piece piece = selectedTile.getPiece();

		if (piece != null) {

			if (piece.getPlayer() == actor) {

				resetTurn();

				if (piece.select()) {
					selectedPiece = piece;
					return SelectionReturnCode.MOVE_PENDING;
				}
			}

			return SelectionReturnCode.INVALID_PIECE_SELECTION;

		} else {
			if (selectedPiece == null) {
				return SelectionReturnCode.INVALID_PIECE_SELECTION;
			}

			if (selectedPiece.tryMove(selectedTile.getCoordinates())) {
				
				board.clearReachables();

				if (selectedPiece.select()) {
					// The piece can still move
					return SelectionReturnCode.MOVE_PENDING;
				} else {
					// The piece cannot move anymore
					currentPlayerHasPlayed = true;							
					return SelectionReturnCode.SUCCESS;
				}
			}

			return SelectionReturnCode.INVALID_TARGET_SELECTION;
		}
	}

	@Override
	protected EndTurnReturnCode specificEndTurn(Player actor) {
		/*
		 * TODO: handle rules like "must select the longest capture sequence"
		 * Already handled (in part) in specificSelect: a player must move when
		 * he can.
		 */

		checkForPieceUpgrade(actor);

		selectedPiece = null;
		
		board.endTurn();
		
		actor.endTurn();

		Player otherPlayer = players.get(0);		

		if (otherPlayer == actor) {
			otherPlayer = players.get(1);
		}
		
		otherPlayer.endTurn();

		List<Piece> otherPlayerPieces = otherPlayer.findOnBoardPieces();

		if (otherPlayerPieces.size() == 0) {
			actor.win();
			otherPlayer.loose();
			return EndTurnReturnCode.SUCCESS;
		}

		for (Piece piece : otherPlayerPieces) {
			if (piece.canMove()) {				
				return EndTurnReturnCode.SUCCESS;
			}
		}

		actor.win();
		otherPlayer.loose();
		return EndTurnReturnCode.SUCCESS;
	}

	@Override
	protected void init() {
		Rules myRules = (Rules) rules;

		List<Piece> player1Pieces = new ArrayList<Piece>();

		List<Piece> player2Pieces = new ArrayList<Piece>();
		
		Player player1, player2;
		
		if (players.get(0).getBoardSide() == DirectionOnBoard.DOWN) {
			player1 = players.get(0);
			player2 = players.get(1);
		} else {
			player1 = players.get(1);
			player2 = players.get(0);
		}

		for (int c = 0; c < myRules.getBoardColumnCount(); c++) {

			for (int r = 0; r < myRules.getNbRowsOfPiece(); r++) {

				Piece p1 = initPiece(player1, r, c);

				if (p1 != null) {
					player1Pieces.add(p1);
				}

				Piece p2 = initPiece(player2, myRules.getBoardRowCount()
						- r - 1, c);

				if (p2 != null) {
					player2Pieces.add(p2);
				}
			}
		}
		
		player1.init(player1Pieces);
		player2.init(player2Pieces);
	}

	protected Piece initPiece(Player owner, int row, int column) {

		Tile tile = board.findTileAt(new Coordinates(row, column));

		Rules myRules = (Rules) rules;

		if (tile != null && myRules.isTileUsed(tile)) {

			Piece piece = new Piece(owner, tile);

			MoveStrategy ms = new ManMoveStrategy(piece, board, myRules);

			piece.setMoveStrategy(ms);

			return piece;
		}

		return null;
	}

	protected void checkForPieceUpgrade(Player actor) {
		int lastRow = 0;

		if (actor.getBoardSide() == DirectionOnBoard.DOWN) {
			lastRow = rules.getBoardRowCount() - 1;
		}

		if (selectedPiece.getCoordinates().getRow() == lastRow) {
			selectedPiece.setMoveStrategy(new KingMoveStrategy(selectedPiece,
					board, (Rules) rules));
		}
	}

	protected void resetTurn() {
		currentPlayerHasPlayed = false;
		board.resetTurn();

		for (Player p : players) {
			p.resetTurn();
		}
	}
}
